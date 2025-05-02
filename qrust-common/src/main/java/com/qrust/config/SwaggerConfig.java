package com.qrust.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "QRust API", description = "QRust : API 명세서", version = "v1.0.0"))
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi qrustOpenApi() {
        return GroupedOpenApi.builder()
                .group("QRust OPEN API")
                .pathsToMatch("/**")
                .build();
    }
}
