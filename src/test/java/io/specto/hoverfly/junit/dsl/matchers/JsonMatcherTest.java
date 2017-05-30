package io.specto.hoverfly.junit.dsl.matchers;


import io.specto.hoverfly.junit.core.model.FieldMatcher;
import io.specto.hoverfly.junit.dsl.HoverflyDslException;
import org.json.JSONObject;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JsonMatcherTest {


    @Test
    public void shouldCreateJsonMatcherFromString() throws Exception {
        FieldMatcher actual = JsonMatcher.createFromString("{\"flightId\":\"1\",\"class\":\"PREMIUM\"}").getFieldMatcher();

        FieldMatcher expected = new FieldMatcher.Builder().jsonMatch("{\"flightId\":\"1\",\"class\":\"PREMIUM\"}").build();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldThrowExceptionIfInputStringIsInvalidJsonFormat() throws Exception {

        assertThatThrownBy(() -> JsonMatcher.createFromString("{\"flightId\":\"1\",\"class\":\"PREMIUM\""))
                .isInstanceOf(HoverflyDslException.class)
                .hasMessageContaining("Fail to create JsonMatcher from invalid JSON string");

    }

    @Test
    public void shouldCreateJsonMatcherFromJSONObject() throws Exception {

        JSONObject object = new JSONObject().put("id", 1);

        FieldMatcher actual = JsonMatcher.createFromString(object.toString()).getFieldMatcher();

        FieldMatcher expected = new FieldMatcher.Builder().jsonMatch("{\"id\":1}").build();

        assertThat(actual).isEqualTo(expected);

    }
}