package com.evk.kafka.giteventsconnector;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.evk.kafka.giteventsconnector.GitEventSinkConnectorConfig.FILE_PATH_PROP;

public class GitEventSinkTask extends SinkTask {
    private Path file;
    private ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(GitEventSinkTask.class);

    @Override

    public String version() {
        return AppInfoParser.getVersion();
    }

    @Override
    public void start(Map<String, String> props) {
        this.file = Paths.get(props.get(FILE_PATH_PROP));
        this.mapper = new ObjectMapper();
    }

    @Override
    public void put(Collection<SinkRecord> records) {
        logger.info("Processing events size {}", records.size());
        List<Object> values = records.stream().map(ConnectRecord::value).collect(Collectors.toList());
        writeRecords(values);
    }

    private void writeRecords(List<Object> values) {
        try {
            Path savedPath = Files.write(
                    file,
                    mapper.writeValueAsString(values).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            logger.warn("Error occurred {}", e.getMessage());
        }
    }

    @Override
    public void stop() {
        logger.info("Stopping the connector");
    }
}
