package io.specto.hoverfly.junit.dsl.matchers;

import io.specto.hoverfly.junit.core.model.FieldMatcher;

public class ExactMatcher implements PlainTextMatcher {

    private String value;
    private FieldMatcher fieldMatcher;

    ExactMatcher(String value) {
        this.value = value;
        this.fieldMatcher = new FieldMatcher(value, null, null, null, null);
    }

    public static FieldMatcher exactlyMatches(String value) {
        return new FieldMatcher(value, null, null, null, null);
    }

    @Override
    public FieldMatcher getFieldMatcher() {
        return fieldMatcher;
    }

    @Override
    public String getValue() {
        return value;
    }
}
