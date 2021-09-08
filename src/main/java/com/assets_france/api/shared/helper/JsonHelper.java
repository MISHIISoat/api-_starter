package com.assets_france.api.shared.helper;

import com.assets_france.api.shared.helper.exception.JsonHelperException;

import java.io.InputStream;

public interface JsonHelper {
    <T> T readInputStreamValue(InputStream value, Class<T> aClass) throws JsonHelperException;
}
