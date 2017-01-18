package io.specto.hoverfly.junit.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import io.specto.hoverfly.junit.core.model.Simulation;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.util.Properties;

import static io.specto.hoverfly.junit.core.HoverflyConfig.configs;
import static io.specto.hoverfly.junit.core.HoverflyMode.SIMULATE;
import static io.specto.hoverfly.junit.core.SimulationSource.classpath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.springframework.http.HttpStatus.OK;

public class HoverflyTest {

    private static final int EXPECTED_PROXY_PORT = 8890;
    private Hoverfly hoverfly;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldStartHoverflyOnConfiguredPort() throws Exception {
        hoverfly = new Hoverfly(configs().proxyPort(EXPECTED_PROXY_PORT), SIMULATE);
        hoverfly.start();
        assertThat(System.getProperty("http.proxyPort")).isEqualTo(String.valueOf(EXPECTED_PROXY_PORT));
        assertThat(hoverfly.getProxyPort()).isEqualTo(EXPECTED_PROXY_PORT);
    }

    @Test
    public void shouldDeleteTempFilesWhenStoppingHoverfly() throws Exception {
        startDefaultHoverfly();
        hoverfly.stop();
        final Field binaryPath = ReflectionUtils.findField(Hoverfly.class, "binaryPath", Path.class);
        binaryPath.setAccessible(true);
        assertThat(Files.exists((Path) binaryPath.get(hoverfly))).isFalse();
    }

    @Test
    public void shouldImportSimulation() throws Exception {
        startDefaultHoverfly();
        // When
        URL resource = Resources.getResource("test-service.json");
        Simulation importedSimulation = mapper.readValue(resource, Simulation.class);
        hoverfly.importSimulation(classpath("test-service.json"));

        // Then
        Simulation exportedSimulation = hoverfly.getSimulation();
        assertThat(exportedSimulation).isEqualTo(importedSimulation);
    }

    @Test
    public void shouldThrowExceptionWhenProxyPortIsAlreadyInUse() throws Exception {
        // Given
        startDefaultHoverfly();
        Hoverfly portClashHoverfly = new Hoverfly(configs().proxyPort(hoverfly.getProxyPort()), SIMULATE);

        try {
            // When
            Throwable throwable = catchThrowable(portClashHoverfly::start);

            //Then
            assertThat(throwable)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Port is already in use");
        } finally {
            portClashHoverfly.stop();
        }
    }

    @Test
    public void shouldThrowExceptionWhenAdminPortIsAlreadyInUse() throws Exception {
        // Given
        startDefaultHoverfly();
        Hoverfly portClashHoverfly = new Hoverfly(configs().adminPort(hoverfly.getAdminPort()), SIMULATE);

        try {
            // When
            Throwable throwable = catchThrowable(portClashHoverfly::start);

            //Then
            assertThat(throwable)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Port is already in use");
        } finally {
            portClashHoverfly.stop();
        }
    }


    @Test
    public void shouldBeAbleToUseARemoteHoverflyDefaultingToLocalhost() throws Exception {
        // Given
        startDefaultHoverfly();
        final int adminPort = hoverfly.getAdminPort();
        final int proxyPort = hoverfly.getProxyPort();
        final Hoverfly hoverfly = new Hoverfly(configs().useRemoteInstance().adminPort(adminPort).proxyPort(proxyPort), SIMULATE);

        // When
        assertRemoteHoverflyIsWorking(hoverfly);
    }

    @Test
    public void shouldBeAbleToUseARemoteHoverflyConfiguringTheHost() throws Exception {
        // Given
        startDefaultHoverfly();
        final int adminPort = hoverfly.getAdminPort();
        final int proxyPort = hoverfly.getProxyPort();
        final Hoverfly remoteHoverfly = new Hoverfly(configs().useRemoteInstance("http://localhost").adminPort(adminPort).proxyPort(proxyPort), SIMULATE);

        // When
        assertRemoteHoverflyIsWorking(remoteHoverfly);
    }

    @Test
    public void shouldDefaultRemoteHoverflyInstancePortsToStaticValues() {
        // Given
        hoverfly = new Hoverfly(configs().proxyPort(8500).adminPort(8888), SIMULATE);
        hoverfly.start();

        // When
        final Hoverfly remoteHoverfly = new Hoverfly(configs().useRemoteInstance("http://localhost"), SIMULATE);

        // When
        assertRemoteHoverflyIsWorking(remoteHoverfly);
    }

    @Test
    public void shouldNotOverrideDefaultTrustManager() throws Exception {
        startDefaultHoverfly();

        HttpClient client = HttpClientBuilder.create().setSSLContext(SSLContext.getDefault()).build();

        // TODO: Find better way to test trust store
        HttpResponse response = client.execute(new HttpGet("https://specto.io"));
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void assertRemoteHoverflyIsWorking(final Hoverfly hoverfly) {
        try {
            hoverfly.start();
            hoverfly.importSimulation(classpath("test-service.json"));
            final ResponseEntity<String> getBookingResponse = new RestTemplate().getForEntity("http://www.my-test.com/api/bookings/1", String.class);

            // Then
            assertThat(hoverfly.getSimulation()).isNotNull();
            assertThat(getBookingResponse.getStatusCode()).isEqualTo(OK);
        } finally {
            hoverfly.stop();
        }
    }


    @After
    public void tearDown() throws Exception {
        if (hoverfly != null) {
            hoverfly.stop();
        }
    }

    private void startDefaultHoverfly() throws IOException, URISyntaxException {
        hoverfly = new Hoverfly(SIMULATE);
        hoverfly.start();
    }
}
