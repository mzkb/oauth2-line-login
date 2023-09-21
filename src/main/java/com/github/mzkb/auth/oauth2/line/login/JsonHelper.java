package com.github.mzkb.auth.oauth2.line.login;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public final class JsonHelper {

    public static Map<String, Object> toJsonMap(InputStream input) {
        try {
            TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
            };
            return new ObjectMapper().readValue(input, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> toJsonMap(String input) {
        try {
            TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
            };
            return new ObjectMapper().readValue(input, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
