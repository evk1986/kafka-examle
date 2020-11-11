package com.evk.kafka.kafkaconsumers.producer;

import com.evk.kafka.kafkaconsumers.dto.GitEvent;
import com.evk.kafka.kafkaconsumers.props.CustomApplicationProperties;
import com.evk.kafka.kafkaconsumers.props.GithubProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class GitHubEventsProducer {

    public static final Logger log = LoggerFactory.getLogger(GitHubEventsProducer.class);

    private final KafkaTemplate<String, GitEvent> kafka;
    private final RestTemplate restTemplate;
    private final GithubProperties git;
    private final CustomApplicationProperties applicationProperties;

    public GitHubEventsProducer(
            @Qualifier("gitEventKafkaTemplate") KafkaTemplate<String, GitEvent> kafka,
            RestTemplate restTemplate,
            GithubProperties git,
            CustomApplicationProperties applicationProperties
    ) {
        this.kafka = kafka;
        this.restTemplate = restTemplate;
        this.git = git;
        this.applicationProperties = applicationProperties;
    }

    public void produce() {
        githubEvents()
                .ifPresent(events -> {
                    Set<CompletableFuture<SendResult<String, GitEvent>>> futures = processEvents(events);
                    CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                            .whenComplete(
                                    (res, err) -> log.info("Producing events had been Finished")
                            );
                });
    }

    private Optional<List<GitEvent>> githubEvents() {
        return Optional.ofNullable(
                restTemplate.exchange(
                        new RequestEntity<>(HttpMethod.GET, git.url),
                        new ParameterizedTypeReference<List<GitEvent>>() {
                        }).getBody()
        );
    }

    private Set<CompletableFuture<SendResult<String, GitEvent>>> processEvents(List<GitEvent> body) {
        return body.stream()
                .map(event -> kafka.send(applicationProperties.getKafkaTopic(), event).completable())
                .map(future -> future.whenComplete((res, err) -> {
                    if (err != null) log.warn(err.getMessage());
                    else log.info("Message: {} processed", res.getProducerRecord().value());
                }))
                .collect(Collectors.toSet());
    }
}
