package com.jacob.service.notify.scheduler;

import com.jacob.common.redis.RedisConstant;
import com.jacob.common.redis.RedisUtils;
import com.jacob.service.notify.MQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 时间轮调度器
 */
@Component
public class TimeWheelScheduler {

    @Autowired
    private MQProducer mqProducer;
    @Autowired
    private RedisUtils redisUtils;

    @Scheduled(fixedDelay = 1000)
    public void scan() {
        long now = System.currentTimeMillis();

        Set<String> tasks = redisUtils.zRangeByScore(RedisConstant.NOTIFY_SCHEDULE.getCode(), 0, now)
                .stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.toSet());

        if(tasks.isEmpty()){
            return;
        }

        for (String reminderId : tasks) {
            mqProducer.send(reminderId);
            redisUtils.zRemove(RedisConstant.NOTIFY_SCHEDULE.getCode(), reminderId);
        }
    }

}
