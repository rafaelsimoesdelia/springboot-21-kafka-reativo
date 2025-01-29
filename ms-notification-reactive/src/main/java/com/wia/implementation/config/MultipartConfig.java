package com.wia.implementation.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import jakarta.servlet.MultipartConfigElement;

/**
 * Configuration class for customizing file upload settings in the application.
 * 
 * This configuration sets the maximum file size and request size for multipart
 * uploads, ensuring the application can handle larger files if required.
 * 
 * @author rsdelia
 */
@Configuration
public class MultipartConfig {

    /**
     * Configures the multipart upload settings, including the maximum file size and
     * the maximum request size.
     *
     * @return A {@link MultipartConfigElement} with the specified upload limits.
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
	MultipartConfigFactory factory = new MultipartConfigFactory();

	// Set the maximum size of an individual uploaded file to 10MB
	factory.setMaxFileSize(DataSize.ofMegabytes(10));

	// Set the maximum size of a request (all uploaded files combined) to 10MB
	factory.setMaxRequestSize(DataSize.ofMegabytes(10));

	return factory.createMultipartConfig();
    }
}
