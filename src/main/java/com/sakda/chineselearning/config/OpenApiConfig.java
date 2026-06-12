package com.sakda.chineselearning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI chineseLearningOpenAPI() {
		
		String securitySchemeName = "bearerAuth";
		
		return new OpenAPI()
				.info(new Info()
						.title("Chinese Learning API")
						.description("Backend API for Chinese learning platform")
						.version("1.0.0")
				)
				.addSecurityItem(
						new SecurityRequirement()
							.addList(securitySchemeName)
						)
				.components(
	                    new Components()
	                            .addSecuritySchemes(
	                                    securitySchemeName,
	                                    new SecurityScheme()
	                                            .name(securitySchemeName)
	                                            .type(SecurityScheme.Type.HTTP)
	                                            .scheme("bearer")
	                                            .bearerFormat("JWT")
	                            )
	            );
	}

}
