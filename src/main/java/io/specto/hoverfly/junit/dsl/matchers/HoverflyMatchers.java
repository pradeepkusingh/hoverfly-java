package io.specto.hoverfly.junit.dsl.matchers;

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

    public static RequestFieldMatcher equalsToJson(Object value) {
        return JsonMatcher.createFromObject(value);
    }

    public static RequestFieldMatcher matchesJsonPath(String expression) {
        return JsonPathMatcher.createFromExpression(expression);
    }

    public static RequestFieldMatcher equalsToXml(String value) {
        return XmlMatcher.createFromString(value);
    }

    public static RequestFieldMatcher equalsToXml(Object value) {
        return XmlMatcher.createFromObject(value);
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
