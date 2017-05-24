package io.specto.hoverfly.junit.dsl.matchers;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HoverflyMatchersTest {


    @Test
    public void matchesShouldCreateGlobMatcher() throws Exception {

        PlainTextMatcher matcher = HoverflyMatchers.matches("fo*o");

        assertThat(matcher).isInstanceOf(GlobMatcher.class);
        assertThat(matcher.getPattern()).isEqualTo("fo*o");
    }

    @Test
    public void startsWithShouldCreateGlobMatcher() throws Exception {
        PlainTextMatcher matcher = HoverflyMatchers.startsWith("foo");

        assertThat(matcher).isInstanceOf(GlobMatcher.class);
        assertThat(matcher.getPattern()).isEqualTo("foo*");
    }


    @Test
    public void endsWithShouldCreateGlobMatcher() throws Exception {
        PlainTextMatcher matcher = HoverflyMatchers.endsWith("foo");

        assertThat(matcher).isInstanceOf(GlobMatcher.class);
        assertThat(matcher.getPattern()).isEqualTo("*foo");
    }

    @Test
    public void containsShouldCreateGlobMatcher() throws Exception {
        PlainTextMatcher matcher = HoverflyMatchers.contains("foo");

        assertThat(matcher).isInstanceOf(GlobMatcher.class);
        assertThat(matcher.getPattern()).isEqualTo("*foo*");
    }

    @Test
    public void anyShouldCreateGlobMatcher() throws Exception {
        PlainTextMatcher matcher = HoverflyMatchers.any();

        assertThat(matcher).isInstanceOf(GlobMatcher.class);
        assertThat(matcher.getPattern()).isEqualTo("*");
    }
}