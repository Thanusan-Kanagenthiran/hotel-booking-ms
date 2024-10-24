package com.tmkproperties.booking.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic bookingTopic() {
        return new NewTopic("booking", 3, (short) 1);

    }
}