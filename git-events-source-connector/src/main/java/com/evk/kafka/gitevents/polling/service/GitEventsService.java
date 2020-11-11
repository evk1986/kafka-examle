package com.evk.kafka.gitevents.polling.service;

import com.evk.kafka.gitevents.polling.dto.GitEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GitEventsService {
    public static final Logger logger = LoggerFactory.getLogger(GitEventsService.class);
    private static GitEventsService instance;
    private final OkHttpClient client = new OkHttpClient();
    private final String url;
    private final ObjectMapper mapper = objectMapper();

    private GitEventsService(String url) {
        this.url = url;
    }

    public static GitEventsService getInstance(String url) {
        if (instance == null) {
            instance = new GitEventsService(url);
        }
        return instance;
    }


    public List<GitEvent> githubEvents() {
        try {
            ResponseBody response = pollEvents();
            return mapper.readValue(response.string(), new TypeReference<List<GitEvent>>() {
            });
        } catch (IOException e) {
            //to prevent worker to stop
            logger.error("Something was wrong {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private ResponseBody pollEvents() throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return client.newCall(request).execute().body();
    }

    private static ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        objectMapper.registerModule(module);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }
}