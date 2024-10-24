package com.tmkproperties.booking.config.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageSender {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaMessageSender(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic,  int partition, String key, Object value) {
        kafkaTemplate.send(topic,partition, key, value);
    }
}