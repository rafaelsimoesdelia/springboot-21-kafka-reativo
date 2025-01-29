package com.wia.implementation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Configuration
public class RestTemplateConfig {

    @Bean
    RestTemplate restTemplate() {
	return new RestTemplate();
    }

}
