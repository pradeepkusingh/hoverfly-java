package io.specto.hoverfly.junit.dsl.matchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.specto.hoverfly.junit.core.model.FieldMatcher;
import io.specto.hoverfly.junit.dsl.HoverflyDslException;
import io.specto.hoverfly.junit.dsl.HttpBodyConverter;

import java.io.IOException;

public class HoverflyMatchers {

    private static final String WILDCARD = "*";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final XmlMapper XML_MAPPER = new XmlMapper();

    private HoverflyMatchers() { }

    public static PlainTextFieldMatcher equalsTo(Object value) {
        return ExactMatcher.newInstance(value);
    }

    public static PlainTextFieldMatcher matches(String value) {
        return GlobMatcher.newInstance(value);
    }

    public static PlainTextFieldMatcher startsWith(String value) {
        return GlobMatcher.newInstance(value + WILDCARD);
    }

    public static PlainTextFieldMatcher endsWith(String value) {
        return GlobMatcher.newInstance(WILDCARD + value);
    }

    // TODO this pattern doesn't work if the target string starts with or ends with the string to match
    public static PlainTextFieldMatcher contains(String value) {
        return GlobMatcher.newInstance(WILDCARD + value + WILDCARD);
    }

    public static PlainTextFieldMatcher any() {
        return GlobMatcher.newInstance(WILDCARD);
    }

    public static RequestFieldMatcher equalsToJson(String value) {
        validateJson(value);
        return () -> new FieldMatcher.Builder().jsonMatch(value).build();
    }

    public static RequestFieldMatcher equalsToJson(HttpBodyConverter converter) {
        return equalsToJson(converter.body());
    }

    public static RequestFieldMatcher matchesJsonPath(String expression) {
        return () -> new FieldMatcher.Builder().jsonPathMatch(expression).build();
    }

    public static RequestFieldMatcher equalsToXml(String value) {
        validateXml(value);
        return () -> new FieldMatcher.Builder().xmlMatch(value).build();
    }

    public static RequestFieldMatcher equalsToXml(HttpBodyConverter converter) {
        return equalsToXml(converter.body());
    }

    public static RequestFieldMatcher matchesXPath(String expression) {
        return () -> new FieldMatcher.Builder().xpathMatch(expression).build();
    }


    private static void validateJson(String value) {
        try {
            OBJECT_MAPPER.readTree(value);
        } catch (IOException e) {
            throw new HoverflyDslException("Fail to create JSON matcher from invalid JSON string: " + value);
        }
    }

    private static void validateXml(String value) {
        try {
            XML_MAPPER.readTree(value);
        } catch (IOException e) {
            throw new HoverflyDslException("Fail to create XML matcher from invalid XML string: " + value);
        }
    }

}
