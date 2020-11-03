package com.evk.kafka.consumer;

import com.evk.kafka.dto.GitEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerToStd {

    public static final Logger log = LoggerFactory.getLogger(ConsumerToStd.class);

    @KafkaListener(topics = {"github.events"}, groupId = "log", containerFactory = "logConsumerFactory")
    public void logConsumer(@Payload GitEvent event) {
        log.info("LOG group received message {}", event);
    }
}
