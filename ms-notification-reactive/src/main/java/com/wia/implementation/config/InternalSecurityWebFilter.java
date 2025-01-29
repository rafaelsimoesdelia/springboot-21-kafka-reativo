package com.wia.implementation.config;

import org.springframework.stereotype.Component;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EqualsAndHashCode(callSuper = false)
public class InternalSecurityWebFilter extends AbstractWebFilter {

    private final Messages messages;

    public InternalSecurityWebFilter(ServletContext servletContext, Messages messages) {
	super(servletContext, messages);
	this.messages = messages;
    }

    private static final String AUTHORIZATION = "Authorization";

    @Override
    public String getSecurityHeaderName() {
	return AUTHORIZATION;
    }

    @Override
    public void validateSecurity(HttpServletRequest httpRequest, String headerName) {
	log.info("validateSecurity internal mode");
    }
}