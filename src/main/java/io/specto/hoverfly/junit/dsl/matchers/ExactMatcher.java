package io.specto.hoverfly.junit.dsl.matchers;

import io.specto.hoverfly.junit.core.model.FieldMatcher;

public class ExactMatcher implements RequestMatcher {

    private String toMatch;
    private FieldMatcher fieldMatcher;

    public ExactMatcher(String toMatch) {
        this.toMatch = toMatch;
        this.fieldMatcher = new FieldMatcher(toMatch, null, null, null, null);
    }

    @Override
    public FieldMatcher getFieldMatcher() {
        return fieldMatcher;
    }

    @Override
    public String getToMatch() {
        return toMatch;
    }
}
