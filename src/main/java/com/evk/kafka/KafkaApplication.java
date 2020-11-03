package com.evk.kafka;

import com.evk.kafka.producer.GitHubEventsProducer;
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
@ConfigurationPropertiesScan("com.evk.kafka.props")
public class KafkaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Autowired
    private GitHubEventsProducer producer;

    @Override
    public void run(String... args) throws Exception {
        producer.produce();
    }
}
