package com.qrust.common.config;


import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi qrustGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("capstone")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(openApi ->
                        openApi.setInfo(new Info()
                                .title("qrust api")
                                .description("QRust API")
                                .version("1.0.0")
                        )
                )
                .build();
    }
}
