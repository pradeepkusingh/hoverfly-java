package io.specto.hoverfly.junit.dsl.matchers;


import io.specto.hoverfly.junit.core.model.FieldMatcher;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExactMatcherTest {

    @Test
    public void shouldCreateExactMatcher() throws Exception {
        FieldMatcher expected = new FieldMatcher.Builder().exactMatch("match.me").build();

        FieldMatcher actual = ExactMatcher.newInstance("match.me").getFieldMatcher();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldExactlyMatchesReturnFieldMatcher() throws Exception {
        FieldMatcher expected = new FieldMatcher.Builder().exactMatch("match.me").build();

        FieldMatcher actual = ExactMatcher.exactlyMatches("match.me");

        assertThat(actual).isEqualTo(expected);
    }
}