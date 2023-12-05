package com.stewart.datatransport.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

/**
 * jackson util of fasterxml.jackson
 *
 * @author stewart
 * @date 2023/1/19
 */
public class JacksonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * resolve bean's structure, and make it to json string
     *
     * @param obj   object
     * @return  json string
     */
    public static String toJsonString(Object obj) {
        try {
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * by using a given json string and a given class, read json string to fill class's structure
     *
     * @param jsonStr   json string
     * @param objClass  target class
     * @return  class's instance which filled by given json string
     * @param <T>   any type
     */
    public static <T> T fromJsonToObject(String jsonStr, Class<T> objClass) {
        try {
            mapper.registerModule(new JavaTimeModule());
            return mapper.readValue(jsonStr, objClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
