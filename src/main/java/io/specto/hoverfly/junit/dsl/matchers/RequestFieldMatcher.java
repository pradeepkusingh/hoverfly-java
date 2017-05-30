package io.specto.hoverfly.junit.dsl.matchers;


import io.specto.hoverfly.junit.core.model.FieldMatcher;

public interface RequestFieldMatcher {



    FieldMatcher getFieldMatcher();

    String getPattern();

}
