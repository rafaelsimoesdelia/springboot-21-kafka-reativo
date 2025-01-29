package com.wia.implementation.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    /**
     * Cria a instância do OpenAPI com as configurações de título, descrição e
     * versão.
     *
     * @param title       O título da API.
     * @param version     A versão da API.
     * @param description A descrição da API.
     * @return a configuração do OpenAPI.
     */
    @Bean
    OpenAPI customOpenAPI(@Value("${springdoc.info.title}") String title,
	    @Value("${springdoc.info.version}") String version,
	    @Value("${springdoc.info.description}") String description) {

	return new OpenAPI().info(new Info().title(title).version(version).description(description))
		.components(new Components()
			.addSecuritySchemes("Bearer Token",
				new SecurityScheme().type(Type.HTTP).scheme("bearer").bearerFormat("JWT").in(In.HEADER)
					.name("Authorization"))
			.addParameters("Referer",
				new Parameter().in("header").name("Referer").description("Referer header")
					.required(true).example("https://example.com")))
		.addSecurityItem(new SecurityRequirement().addList("Bearer Token"));
    }

    /**
     * Configura os grupos de APIs que serão exibidos no Swagger, baseados no pacote
     * base.
     *
     * @param basePackage O pacote base para selecionar os endpoints.
     * @return uma instância de GroupedOpenApi.
     */
    @Bean
    GroupedOpenApi apiGroup(@Value("${springdoc.base-package}") String basePackage) {
	return GroupedOpenApi.builder().group("v1").packagesToScan(basePackage).build();
    }
}
