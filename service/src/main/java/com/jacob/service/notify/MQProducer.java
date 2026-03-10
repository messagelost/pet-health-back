package com.jacob.service.notify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Kafka消息生产者
 */
@Service
public class MQProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String ruleId) {

        kafkaTemplate.send("notify-topic", ruleId);

    }
}
