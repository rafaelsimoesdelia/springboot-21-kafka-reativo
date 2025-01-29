package com.wia.implementation.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "com.wia.implementation" })
@EntityScan(basePackages = { "com.wia.implementation" })
public class JpaConfig {

}
