package com.qrust.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi qrustGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("capstone")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("QRust API")
                        .description("QRust API")
                        .version("1.0.0")
                )
                .servers(List.of(
                        new Server().url("https://mayfifth99.store").description("운영 서버"),
                        new Server().url("http://localhost:8080").description("로컬 서버")
                ))
                ;
    }
}
