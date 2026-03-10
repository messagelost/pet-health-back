package com.jacob.service.notify;

import com.jacob.service.petData.PetEventReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka消息消费者
 */
@Component
public class NotifyConsumer {

    @Autowired
    private PetEventReminderService petEventReminderService;

    @KafkaListener(topics = "notify-topic")
    public void consume(String reminderId) {

        petEventReminderService.process(reminderId);

    }
}