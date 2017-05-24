package io.specto.hoverfly.junit.dsl.matchers;

import io.specto.hoverfly.junit.core.model.FieldMatcher;

public interface RequestMatcher {

    static GlobMatcher matches(String toMatch) {
        return new GlobMatcher(toMatch);
    }


    FieldMatcher getFieldMatcher();

    String getToMatch();


//    FieldMatcher startsWith(String toMatch);
//
//    FieldMatcher endsWith(String toMatch);
//
//    FieldMatcher contains(String toMatch);
//
//    FieldMatcher any();
}
