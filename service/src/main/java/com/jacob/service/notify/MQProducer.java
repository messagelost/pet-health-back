package com.jacob.service.notify;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Kafka消息生产者
 */
@Slf4j
@Service
public class MQProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String ruleId) {

        kafkaTemplate.send("notify-topic", ruleId)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Kafka发送失败: {}", ruleId);
                    }
                });

    }
}
