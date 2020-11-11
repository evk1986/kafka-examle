package com.evk.kafka.gitevents.polling.connector;

import com.evk.kafka.gitevents.polling.service.GitEventsService;
import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.evk.kafka.gitevents.polling.connector.GitEventSourceConnectorConfig.API_URL;

public class GitEventSourceTask extends SourceTask {
    private static final Logger logger = LoggerFactory.getLogger(GitEventSourceTask.class);
    public String topic;
    private GitEventsService service;

    @Override
    public List<SourceRecord> poll() {
        return service.githubEvents()
                .stream()
                .map(event -> new SourceRecord(
                        new HashMap<>(),
                        new HashMap<>(),
                        topic,
                        Schema.STRING_SCHEMA,
                        Schema.STRING_SCHEMA

                ))
                .collect(Collectors.toList());

    }

    @Override
    public String version() {
        return AppInfoParser.getVersion();
    }

    @Override
    public void start(Map<String, String> props) {
        logger.info("Starting the connector");
        this.topic = props.get("topic");
        this.service = GitEventsService.getInstance(props.get(API_URL));
    }

    @Override
    public void stop() {
        logger.info("Stopping the connector");
    }
}
