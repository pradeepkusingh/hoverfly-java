package io.specto.hoverfly.junit.dsl.matchers;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HoverflyMatchersTest {


    @Test
    public void matchesShouldCreateGlobMatcher() throws Exception {

        PlainTextFieldMatcher matcher = HoverflyMatchers.matches("fo*o");

        assertThat(matcher).isInstanceOf(GlobMatcher.class);
        assertThat(matcher.getPattern()).isEqualTo("fo*o");
    }

    @Test
    public void startsWithShouldCreateGlobMatcher() throws Exception {
        PlainTextFieldMatcher matcher = HoverflyMatchers.startsWith("foo");

        assertThat(matcher).isInstanceOf(GlobMatcher.class);
        assertThat(matcher.getPattern()).isEqualTo("foo*");
    }


    @Test
    public void endsWithShouldCreateGlobMatcher() throws Exception {
        PlainTextFieldMatcher matcher = HoverflyMatchers.endsWith("foo");

        assertThat(matcher).isInstanceOf(GlobMatcher.class);
        assertThat(matcher.getPattern()).isEqualTo("*foo");
    }

    @Test
    public void containsShouldCreateGlobMatcher() throws Exception {
        PlainTextFieldMatcher matcher = HoverflyMatchers.contains("foo");

        assertThat(matcher).isInstanceOf(GlobMatcher.class);
        assertThat(matcher.getPattern()).isEqualTo("*foo*");
    }

    @Test
    public void anyShouldCreateGlobMatcher() throws Exception {
        PlainTextFieldMatcher matcher = HoverflyMatchers.any();

        assertThat(matcher).isInstanceOf(GlobMatcher.class);
        assertThat(matcher.getPattern()).isEqualTo("*");
    }
}