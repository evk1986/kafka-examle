package com.evk.kafka.giteventsconnector;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class GitEventSinkConnectorConfig extends AbstractConfig {

    public static final Logger logger = LoggerFactory.getLogger(GitEventSinkConnectorConfig.class);
    public static final String FILE_PATH_PROP = "file.output.path";
    public static final String TOPIC_PROP = "topics";

    public GitEventSinkConnectorConfig(Map<?, ?> originals) {
        super(configuration(), originals);
    }

    public static ConfigDef configuration() {
        logger.info("Properties initialization");
        return new ConfigDef()
                .define(FILE_PATH_PROP,
                        ConfigDef.Type.STRING,
                        ConfigDef.Importance.HIGH,
                        "File for storing events from topic")
                .define(TOPIC_PROP,
                        ConfigDef.Type.LIST,
                        ConfigDef.Importance.HIGH,
                        "Name of Kafka topic to read from");
    }
}
