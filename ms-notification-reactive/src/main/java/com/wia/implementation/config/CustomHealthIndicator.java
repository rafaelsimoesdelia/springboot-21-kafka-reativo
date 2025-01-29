package com.wia.implementation.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
	boolean isHealthy = checkCustomHealth();
	return isHealthy ? Health.up().build() : Health.down().withDetail("Error", "Custom check failed").build();
    }

    private boolean checkCustomHealth() {
	return true;
    }
}
