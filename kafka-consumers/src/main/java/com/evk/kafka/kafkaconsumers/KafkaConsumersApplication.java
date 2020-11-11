package com.evk.kafka.kafkaconsumers;

import com.evk.kafka.kafkaconsumers.producer.GitHubEventsProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableConfigurationProperties
@EnableKafka
@ConfigurationPropertiesScan("com.evk.kafka.kafkaconsumers.props")
public class KafkaConsumersApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumersApplication.class, args);
    }

    @Autowired
    private GitHubEventsProducer producer;

    @Override
    public void run(String... args) throws Exception {
        producer.produce();
    }

}
