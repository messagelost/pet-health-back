package com.jacob.service.notify;

import com.jacob.common.redis.RedisConstant;
import com.jacob.common.redis.RedisUtils;
import com.jacob.service.petData.PetEventReminderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka消息消费者
 */
@Slf4j
@Component
public class NotifyConsumer {

    @Autowired
    private PetEventReminderService petEventReminderService;
    @Autowired
    private RedisUtils redisUtils;

    @KafkaListener(topics = "notify-topic")
    public void consume(String reminderId) {

        try {

            petEventReminderService.process(reminderId);
            // 从处理队列中移除
            redisUtils.zRemove(RedisConstant.NOTIFY_PROCESSING.getCode(), reminderId);

        }catch (Exception e) {

            log.error("发送: {} 失败，服务异常", reminderId, e);

        }


    }
}