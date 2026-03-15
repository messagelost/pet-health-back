package com.jacob.service.notify.scheduler;

import com.jacob.common.redis.RedisConstant;
import com.jacob.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 重试调度器
 */
@Component
public class RetryScheduler {

    @Autowired
    private RedisUtils redisUtils;

    @Scheduled(fixedDelay = 60000)
    public void retry() {

        long timeout = System.currentTimeMillis() - 60000;

        Set<Object> tasks =
                redisUtils.zRangeByScore(RedisConstant.NOTIFY_PROCESSING.getCode(), 0, timeout);

        for (Object obj : tasks) {

            String taskId = obj.toString();

            redisUtils.zRemove(RedisConstant.NOTIFY_PROCESSING.getCode(), taskId);
            redisUtils.zAdd(RedisConstant.NOTIFY_RETRY.getCode(), taskId, System.currentTimeMillis());

        }

    }
}
