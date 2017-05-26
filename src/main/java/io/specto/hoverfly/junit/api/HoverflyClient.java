package io.specto.hoverfly.junit.api;

import io.specto.hoverfly.junit.core.HoverflyMode;
import io.specto.hoverfly.junit.core.model.HoverflyInfo;
import io.specto.hoverfly.junit.core.model.Simulation;

/**
 * Http client for querying Hoverfly admin endpoints
 */
public interface HoverflyClient {


    void setSimulation(Simulation simulation);

    Simulation getSimulation();

    HoverflyInfo getConfigInfo();

    void setDestination(String destination);

    void setMode(HoverflyMode mode);

    /**
     * Check Hoverfly is healthy
     * @return the status of Hoverfly
     */
    boolean getHealth();

    /**
     * Static factory method for creating a {@link HoverflyClientBuilder}
     * @return a builder for HoverflyClient
     */
    static HoverflyClientBuilder custom() {
        return new HoverflyClientBuilder();
    }

    /**
     * Static factory method for default Hoverfly client
     * @return a default HoverflyClient
     */
    static HoverflyClient createDefault() {
        return new HoverflyClientBuilder().build();
    }
}
