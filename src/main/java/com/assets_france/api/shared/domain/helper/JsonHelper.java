package com.assets_france.api.shared.domain.helper;

import com.assets_france.api.shared.domain.exception.JsonHelperException;

import java.io.InputStream;

public interface JsonHelper {
    <T> T readInputStreamValue(InputStream value, Class<T> aClass) throws JsonHelperException;
}
