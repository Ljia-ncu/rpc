package org.fullstack.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fullstack.model.ExposedBean;

import java.io.IOException;

public class JsonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtil() {
    }

    public static String toJsonStr(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parse(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toJsonByte(Object obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parse(byte[] data, Class<T> type) {
        try {
            return objectMapper.readValue(data, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
