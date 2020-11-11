package com.evk.kafka.kafkaconsumers.producer;

import com.evk.kafka.kafkaconsumers.dto.GitEvent;
import com.evk.kafka.kafkaconsumers.props.CustomApplicationProperties;
import com.evk.kafka.kafkaconsumers.props.GithubProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfig {

    private final CustomApplicationProperties properties;
    private final GithubProperties git;
    private final KafkaProperties kafkaProps;


    public ProducerConfig(CustomApplicationProperties properties, GithubProperties git, KafkaProperties kafkaProps) {
        this.properties = properties;
        this.git = git;
        this.kafkaProps = kafkaProps;

    }

    @Bean
    public ProducerFactory<String, GitEvent> pf(@Qualifier("kafkaMapper") ObjectMapper mapper) {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaProps.getBootstrapServers());
        return new DefaultKafkaProducerFactory<>(props, new StringSerializer(), new JsonSerializer<>(mapper));
    }

    @Bean
    public KafkaTemplate<String, GitEvent> gitEventKafkaTemplate(ProducerFactory<String, GitEvent> pf) {
        return new KafkaTemplate<>(pf);
    }

    @Bean
    public NewTopic gitEventsTopic() {
        return TopicBuilder
                .name(properties.getKafkaTopic())
                .partitions(properties.getKafkaPartitions())
                .replicas(properties.getKafkaReplicas())
                .build();
    }

    @Bean
    public RestTemplate gitHubRestTemplate() {
        return new RestTemplateBuilder()
                .rootUri(git.url.toString())
                .build();

    }


}
