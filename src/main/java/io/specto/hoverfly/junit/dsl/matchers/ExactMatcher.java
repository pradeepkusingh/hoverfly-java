package io.specto.hoverfly.junit.dsl.matchers;

import io.specto.hoverfly.junit.core.model.FieldMatcher;

public class ExactMatcher implements PlainTextMatcher {

    private String pattern;
    private FieldMatcher fieldMatcher;

    private ExactMatcher(String pattern) {
        this.pattern = pattern;
        this.fieldMatcher = exactlyMatches(pattern);
    }


    public static ExactMatcher newInstance(String value) {
        return new ExactMatcher(value);
    }

    public static FieldMatcher exactlyMatches(String value) {
        return new FieldMatcher.Builder().exactMatch(value).build();
    }

    @Override
    public FieldMatcher getFieldMatcher() {
        return fieldMatcher;
    }

    @Override
    public String getPattern() {
        return pattern;
    }
}
