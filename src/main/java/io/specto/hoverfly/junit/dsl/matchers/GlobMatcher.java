package io.specto.hoverfly.junit.dsl.matchers;

import io.specto.hoverfly.junit.core.model.FieldMatcher;

public class GlobMatcher implements PlainTextMatcher {

    private FieldMatcher fieldMatcher;

    private String value;

    public GlobMatcher(String value) {
        this.value = value;
        this.fieldMatcher = new FieldMatcher(null, value, null, null, null);
    }


    @Override
    public FieldMatcher getFieldMatcher() {
        return this.fieldMatcher;
    }

    @Override
    public String getValue() {
        return value;
    }
}
