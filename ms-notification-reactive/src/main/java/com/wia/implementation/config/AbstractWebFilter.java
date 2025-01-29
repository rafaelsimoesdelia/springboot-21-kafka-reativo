package com.wia.implementation.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wia.implementation.constants.ConstantsMessages;
import com.wia.implementation.exception.ARLeptonSessionException;
import com.wia.implementation.response.GenericResponse;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractWebFilter implements Filter {

    private static final String ORIGIN = "origin";

    private final ServletContext servletContext;

    private final Messages messages;

    @Value("${arlepton.features.cors.enable}")
    private boolean corsEnabled;

    @Value("${arlepton.features.cors.allowedOrigins}")
    private String[] allowedOrigins;

    private boolean allowed;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
	HttpServletRequest httpRequest = (HttpServletRequest) request;
	HttpServletResponse httpResponse = (HttpServletResponse) response;

	httpResponse.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization , apiKey");
	httpResponse.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
	httpResponse.addHeader("Access-Control-Allow-Credentials", "true");

	validateOrigin(httpRequest, httpResponse);

	var authorizedOrigin = (httpRequest.getHeader(ORIGIN) != null) ? httpRequest.getHeader(ORIGIN)
		: StringUtils.EMPTY;
	httpResponse.addHeader("Access-Control-Allow-Origin", authorizedOrigin);

	if (httpRequest.getMethod().equals("OPTIONS")) {
	    httpResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
	    return;
	}

	try {
	    validateSecurity(httpRequest, getSecurityHeaderName());
	} catch (ARLeptonSessionException e) {
	    log.error(e.getMessage());
	    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
	    httpResponse.setContentType("application/json");
	    httpResponse.setCharacterEncoding("UTF-8");

	    // Criar o objeto GenericResponse
	    GenericResponse<Object> genericResponse = new GenericResponse<>(HttpServletResponse.SC_UNAUTHORIZED,
		    "Unauthorized", "Invalid token: " + e.getMessage(), null);

	    // Converter o objeto GenericResponse para JSON
	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonResponse = objectMapper.writeValueAsString(genericResponse);

	    // Escrever a resposta JSON
	    httpResponse.getWriter().write(jsonResponse);
	    httpResponse.getWriter().flush(); // Garante que o JSON seja enviado
	    return; // Interrompe a execução do filtro
	}

	chain.doFilter(request, response);
    }

    abstract String getSecurityHeaderName();

    abstract void validateSecurity(HttpServletRequest httpRequest, final String headerName);

    @Override
    public void init(FilterConfig filterConf) throws ServletException {

    }

    @Override
    public void destroy() {
    }

    private void validateOrigin(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
	if (corsEnabled && StringUtils.isNotBlank(httpRequest.getHeader(ORIGIN))) {
	    List<String> acceptedOrigins = new ArrayList<>();
	    Collections.addAll(acceptedOrigins, allowedOrigins);
	    if (!acceptedOrigins.contains(httpRequest.getHeader(ORIGIN))) {
		log.warn("Access not authorised because the origin is not configured: {}",
			httpRequest.getHeader(ORIGIN));
		httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(),
			messages.get(ConstantsMessages.ORIGIN_NAO_TEM_PERMISSAO));
	    }
	}
    }
}