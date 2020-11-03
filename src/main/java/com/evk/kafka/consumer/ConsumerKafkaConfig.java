package com.evk.kafka.consumer;

import com.evk.kafka.dto.GitEvent;
import com.evk.kafka.dto.GitEventType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerKafkaConfig {
    private final KafkaProperties kafkaProps;
    private final ObjectMapper objectMapper;

    public ConsumerKafkaConfig(KafkaProperties kafkaProps, @Qualifier("kafkaMapper") ObjectMapper objectMapper) {
        this.kafkaProps = kafkaProps;
        this.objectMapper = objectMapper;
    }


    @Bean
    public ConsumerFactory<String, GitEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaProps.getBootstrapServers());
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(GitEvent.class, objectMapper));
    }

    public RecordFilterStrategy<String, GitEvent> recordFilterStrategy() {
        return consumerRecord -> consumerRecord.value().getType() != GitEventType.PushEvent;
    }


    @Bean("fileConsumerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, GitEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, GitEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean("logConsumerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, GitEvent> logConsumerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, GitEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setRecordFilterStrategy(recordFilterStrategy());
        return factory;
    }

}
