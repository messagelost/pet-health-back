package com.jacob.service.notify.scheduler;

import com.jacob.common.redis.RedisConstant;
import com.jacob.common.redis.RedisUtils;
import com.jacob.service.notify.MQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 时间轮调度器
 */
@Slf4j
@Component
public class TimeWheelScheduler {

    @Autowired
    private MQProducer mqProducer;
    @Autowired
    private RedisUtils redisUtils;

    @Scheduled(fixedDelay = 1000)
    public void scan() {
        long now = System.currentTimeMillis();

        Set<String> tasks = redisUtils.zRangeByScore(RedisConstant.NOTIFY_SCHEDULE.getCode(), 0, now, 0, 100)
                .stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.toSet());

        if(tasks.isEmpty()){
            return;
        }

        for (String taskId : tasks) {

            try {
                // 移动到 processing
                redisUtils.zRemove(RedisConstant.NOTIFY_SCHEDULE.getCode(), taskId);
                redisUtils.zAdd(RedisConstant.NOTIFY_PROCESSING.getCode(), taskId, now);

                log.info("调度任务: {}", taskId);

                mqProducer.send(taskId);

            } catch (Exception e) {

                log.error("任务发送失败 {}，Kafka异常", taskId, e);

                // 回滚任务
                redisUtils.zAdd(RedisConstant.NOTIFY_SCHEDULE.getCode(), taskId, now);

            }

        }
    }

    @Scheduled(fixedDelay = 30000)
    public void retryScan() {
        long now = System.currentTimeMillis();

        Set<String> tasks = redisUtils.zRangeByScore(RedisConstant.NOTIFY_RETRY.getCode(), 0, System.currentTimeMillis()).stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.toSet());

        for (String taskId : tasks) {

            try {
                // 移动到 processing
                redisUtils.zRemove(RedisConstant.NOTIFY_RETRY.getCode(), taskId);
                redisUtils.zAdd(RedisConstant.NOTIFY_PROCESSING.getCode(), taskId, now);

                log.info("重试任务: {}", taskId);

                mqProducer.send(taskId);

            } catch (Exception e) {

                log.error("任务重试发送失败 {}，Kafka异常", taskId, e);

                // 回滚任务
                redisUtils.zAdd(RedisConstant.NOTIFY_RETRY.getCode(), taskId, now);

            }

        }

    }

}
