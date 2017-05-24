package io.specto.hoverfly.junit.dsl.matchers;

public class HoverflyMatchers {


    static PlainTextMatcher isEqual(String value) {
        return new ExactMatcher(value);
    }

    public static PlainTextMatcher matches(String value) {
        return new GlobMatcher(value);
    }

    public static RequestMatcher equalsToJson(String value) {
        return new JsonMatcher();
    }

}
