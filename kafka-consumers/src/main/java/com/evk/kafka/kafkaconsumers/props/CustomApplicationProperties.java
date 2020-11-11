package com.evk.kafka.kafkaconsumers.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "app.properties")
@ConstructorBinding
public class CustomApplicationProperties {

    private String kafkaTopic;
    private int kafkaReplicas;
    private int kafkaPartitions;

    public CustomApplicationProperties(String kafkaTopic, int kafkaReplicas, int kafkaPartitions) {
        this.kafkaTopic = kafkaTopic;
        this.kafkaReplicas = kafkaReplicas;
        this.kafkaPartitions = kafkaPartitions;
    }

    public int getKafkaReplicas() {
        return kafkaReplicas;
    }

    public void setKafkaReplicas(int kafkaReplicas) {
        this.kafkaReplicas = kafkaReplicas;
    }

    public int getKafkaPartitions() {
        return kafkaPartitions;
    }

    public void setKafkaPartitions(int kafkaPartitions) {
        this.kafkaPartitions = kafkaPartitions;
    }

    public String getKafkaTopic() {
        return kafkaTopic;
    }

    public void setKafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }
}
