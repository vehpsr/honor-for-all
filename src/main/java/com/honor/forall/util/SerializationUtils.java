package com.honor.forall.util;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class SerializationUtils {

    private SerializationUtils() {}

    private static final ObjectMapper MAPPER = new ObjectMapper();
    static {
        MAPPER.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, Class<T> t) {
        try {
            return MAPPER.readValue(json, t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
