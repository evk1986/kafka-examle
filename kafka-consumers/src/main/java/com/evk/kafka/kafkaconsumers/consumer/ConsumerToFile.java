package com.evk.kafka.kafkaconsumers.consumer;

import com.evk.kafka.kafkaconsumers.dto.GitEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
public class ConsumerToFile {

    public static final Path OUTPUT = Paths.get("events.json");
    public final ObjectMapper mapper;

    public ConsumerToFile(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @KafkaListener(topics = {"github.events"}, groupId = "file", containerFactory = "fileConsumerFactory")
    public void logConsumer(@Payload GitEvent event) throws IOException {
        Files.write(
                OUTPUT,
                mapper.writeValueAsString(event).getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

}
