package io.specto.hoverfly.junit.dsl.matchers;

import io.specto.hoverfly.junit.core.model.FieldMatcher;

public class GlobMatcher implements RequestMatcher {

    private FieldMatcher fieldMatcher;

    private String toMatch;

    public GlobMatcher(String toMatch) {
        this.toMatch = toMatch;
        this.fieldMatcher = new FieldMatcher(null, toMatch, null, null, null);
    }


    @Override
    public FieldMatcher getFieldMatcher() {
        return this.fieldMatcher;
    }

    @Override
    public String getToMatch() {
        return toMatch;
    }
}
