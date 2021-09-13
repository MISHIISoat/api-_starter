package com.assets_france.api.shared.infrastructure.exception.helper.json;

import com.assets_france.api.shared.domain.exception.JsonHelperException;
import com.assets_france.api.shared.domain.helper.JsonHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class JsonHelperImpl implements JsonHelper {
    private final ObjectMapper objectMapper;
    public JsonHelperImpl() {
        objectMapper = new ObjectMapper();
    }

    public <T> T readInputStreamValue(InputStream value, Class<T> aClass) throws JsonHelperException {
        try {
            return objectMapper.readValue(value, aClass);
        } catch (IOException exception) {
            throw new JsonHelperException(exception.getMessage());
        }
    }

    @Override
    public <T> T readStringValue(String value, Class<T> aClass) throws JsonHelperException {
        try {
            return objectMapper.readValue(value, aClass);
        } catch (JsonProcessingException exception) {
            throw new JsonHelperException(exception.getMessage());
        }
    }

    @Override
    public String objectToJson(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
