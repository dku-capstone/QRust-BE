package com.qrust.common.config;

import com.qrust.external.google.infrastructure.GoogleSafeBrowsingFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = GoogleSafeBrowsingFeignClient.class)
public class FeignConfig {
}
