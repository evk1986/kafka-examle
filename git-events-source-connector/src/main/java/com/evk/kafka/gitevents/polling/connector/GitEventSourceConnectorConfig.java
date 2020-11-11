package com.evk.kafka.gitevents.polling.connector;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class GitEventSourceConnectorConfig extends AbstractConfig {

    public static final Logger logger = LoggerFactory.getLogger(GitEventSourceConnectorConfig.class);
    public static final String TOPIC_PROP = "topic";
    public static final String API_URL = "git.hub.api.url";

    public GitEventSourceConnectorConfig(Map<?, ?> originals) {
        super(configuration(), originals);
    }

    public static ConfigDef configuration() {
        logger.info("Properties initialization");
        return new ConfigDef()
                .define(API_URL,
                        ConfigDef.Type.STRING,
                        ConfigDef.Importance.HIGH,
                        "File for storing events from topic")
                .define(TOPIC_PROP,
                        ConfigDef.Type.STRING,
                        ConfigDef.Importance.HIGH,
                        "Name of Kafka topic to read from");
    }
}
