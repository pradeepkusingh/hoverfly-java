package io.specto.hoverfly.junit.dsl.matchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.specto.hoverfly.junit.core.model.FieldMatcher;
import io.specto.hoverfly.junit.dsl.HoverflyDslException;

import java.io.IOException;

public class JsonMatcher implements RequestFieldMatcher {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private String pattern;
    private FieldMatcher fieldMatcher;

    private JsonMatcher(String pattern) {
        this.pattern = pattern;
        this.fieldMatcher = new FieldMatcher.Builder().jsonMatch(pattern).build();
    }

    @Override
    public FieldMatcher getFieldMatcher() {
        return fieldMatcher;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    static JsonMatcher createFromString(String value) {
        validateJson(value);
        return new JsonMatcher(value);
    }


    private static void validateJson(String value) {
        try {
            OBJECT_MAPPER.readTree(value);
        } catch (IOException e) {
            throw new HoverflyDslException("Fail to create JsonMatcher from invalid JSON string: " + value);
        }
    }
}
