package com.wia.implementation.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

/**
 * A filter that adds a key to the Mapped Diagnostic Context (MDC) to each
 * request so you can print a unique id in the log messages of eachrequest
 * 
 * @author rsdelia
 *
 */
@Configuration
@RequiredArgsConstructor
public class Slf4jMDCConfig {
    private final Slf4jMDCFilter slf4jMDCFilter;

    @Bean
    FilterRegistrationBean<Slf4jMDCFilter> servletRegistrationBean() {
	final FilterRegistrationBean<Slf4jMDCFilter> filterRegistrationBean = new FilterRegistrationBean<>();
	filterRegistrationBean.setFilter(slf4jMDCFilter);
	filterRegistrationBean.setOrder(2);
	return filterRegistrationBean;
    }
}
