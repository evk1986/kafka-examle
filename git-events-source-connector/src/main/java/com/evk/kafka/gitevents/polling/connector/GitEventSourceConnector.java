package com.evk.kafka.gitevents.polling.connector;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GitEventSourceConnector extends SourceConnector {
    public static final Logger logger = LoggerFactory.getLogger(GitEventSourceConnector.class);

    private GitEventSourceConnectorConfig config;
    private final Map<String, String> configProps = new HashMap<>();

    @Override
    public void start(Map<String, String> props) {
        config = new GitEventSourceConnectorConfig(props);
        for (Map.Entry<String, Object> entry : GitEventSourceConnectorConfig.configuration().parse(props).entrySet()) {
            logger.info("property initializing key {} value {}", entry.getKey(), entry.getValue().toString());
            this.configProps.put(entry.getKey(), entry.getValue().toString());
        }
    }

    @Override
    public Class<? extends Task> taskClass() {
        return GitEventSourceTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        List<Map<String, String>> taskConfigs = new ArrayList<>();
        for (int i = 0; i < maxTasks; i++) {
            taskConfigs.add(new HashMap<>(configProps));
        }
        return taskConfigs;
    }

    @Override
    public void stop() {
        logger.info("Stopping connector");
    }

    @Override
    public ConfigDef config() {
        return GitEventSourceConnectorConfig.configuration();
    }

    @Override
    public String version() {
        return AppInfoParser.getVersion();
    }
}
