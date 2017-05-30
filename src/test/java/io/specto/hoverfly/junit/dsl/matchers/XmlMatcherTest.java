package io.specto.hoverfly.junit.dsl.matchers;


import io.specto.hoverfly.junit.core.model.FieldMatcher;
import io.specto.hoverfly.junit.dsl.HoverflyDslException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class XmlMatcherTest {


    @Test
    public void shouldCreateXmlMatcherFromString() throws Exception {
        FieldMatcher actual = XmlMatcher.createFromString("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <flightId>1</flightId> <class>PREMIUM</class>").getFieldMatcher();

        FieldMatcher expected = new FieldMatcher.Builder().xmlMatch("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <flightId>1</flightId> <class>PREMIUM</class>").build();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldThrowExceptionIfInputStringIsInvalidXmlFormat() throws Exception {

        assertThatThrownBy(() -> XmlMatcher.createFromString("{\"flightId\":\"1\",\"class\":\"PREMIUM\""))
                .isInstanceOf(HoverflyDslException.class)
                .hasMessageContaining("Fail to create JsonMatcher from invalid Xml string");

    }
}