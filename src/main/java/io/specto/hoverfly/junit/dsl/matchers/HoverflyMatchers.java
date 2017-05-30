package io.specto.hoverfly.junit.dsl.matchers;

import io.specto.hoverfly.junit.dsl.HttpBodyConverter;

public class HoverflyMatchers {

    public static PlainTextFieldMatcher matches(String value) {
        return GlobMatcher.createFromPattern(value);
    }

    public static PlainTextFieldMatcher startsWith(String value) {
        return GlobMatcher.createFromStringFormat("%s*", value);
    }

    public static RequestFieldMatcher equalsToJson(String value) {
        return JsonMatcher.createFromString(value);
    }

    public static RequestFieldMatcher equalsToJson(HttpBodyConverter converter) {
        return JsonMatcher.createFromString(converter.body());
    }

    public static RequestFieldMatcher matchesJsonPath(String expression) {
        return JsonPathMatcher.createFromExpression(expression);
    }

    public static RequestFieldMatcher equalsToXml(String value) {
        return XmlMatcher.createFromString(value);
    }

    public static RequestFieldMatcher equalsToXml(HttpBodyConverter converter) {
        return XmlMatcher.createFromString(converter.body());
    }

    public static RequestFieldMatcher matchesXPath(String expression) {
        return XPathMatcher.createFromExpression(expression);
    }

    public static PlainTextFieldMatcher endsWith(String value) {
        return GlobMatcher.createFromStringFormat("*%s", value);
    }

    // TODO this pattern doesn't work if the target string starts with or ends with the string to match
    public static PlainTextFieldMatcher contains(String value) {
        return GlobMatcher.createFromStringFormat("*%s*", value);

    }

    public static PlainTextFieldMatcher any() {
        return GlobMatcher.createFromPattern("*");
    }
}
