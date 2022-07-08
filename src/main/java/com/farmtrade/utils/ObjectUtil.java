package com.farmtrade.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ObjectUtil {
    public static <T> T patchObject(Map<String, String> source, Map<String, String> target, Class<T> cls) {
        final ObjectMapper mapper = new ObjectMapper();
        target.keySet().stream().forEach(key -> {
            String value = source.get(key) == null ? target.get(key) : source.get(key);
            target.put(key, value);
        });
        return mapper.convertValue(target, cls);
    }

}
