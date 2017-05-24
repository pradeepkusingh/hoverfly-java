package io.specto.hoverfly.ruletest;

import io.specto.hoverfly.junit.dsl.HttpBodyConverter;
import io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import io.specto.hoverfly.models.SimpleBooking;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

public class HoverflyRuleDslMatcherTest {

    private RestTemplate restTemplate = new RestTemplate();

    private static SimpleBooking booking = new SimpleBooking(1, "London", "Hong Kong", LocalDate.now());

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule.inSimulationMode(dsl(

            // Matcher for url
            service(HoverflyMatchers.matches("www.*-test.com"))
                    .get("/api/bookings/1")
                    .willReturn(success(HttpBodyConverter.json(booking)))
    ));



    @Test
    public void shouldBeAbleToQueryBookingsUsingHoverfly() throws Exception {
        // When
        URI uri = UriComponentsBuilder.fromHttpUrl("http://www.my-test.com")
                .path("/api/bookings/1")
                .build()
                .toUri();
        final ResponseEntity<SimpleBooking> response = restTemplate.getForEntity(uri, SimpleBooking.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isEqualTo(booking);

    }

}
