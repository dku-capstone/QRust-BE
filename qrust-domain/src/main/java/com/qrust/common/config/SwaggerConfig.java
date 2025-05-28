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
                        .description("QRust 프로젝트용 API 문서입니다.")
                        .version("1.0.0")
                )
                .servers(List.of(
                        new Server()
                                .url("https://localhost:8080")
                                .description("로컬 테스트용 HTTPS 서버")
                ));
    }
}
