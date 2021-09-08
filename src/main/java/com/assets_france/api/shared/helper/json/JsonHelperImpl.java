package com.assets_france.api.shared.helper.json;

import com.assets_france.api.shared.domain.helper.JsonHelper;
import com.assets_france.api.shared.domain.exception.JsonHelperException;
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
}
