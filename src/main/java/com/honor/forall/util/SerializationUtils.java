package com.honor.forall.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class SerializationUtils {

    private SerializationUtils() {}

    private static final ObjectMapper MAPPER = new ObjectMapper();

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
