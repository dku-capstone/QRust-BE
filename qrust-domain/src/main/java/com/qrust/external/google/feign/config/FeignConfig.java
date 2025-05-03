package com.qrust.external.google.feign.config;

import com.qrust.external.google.feign.client.GoogleSafeBrowsingFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = GoogleSafeBrowsingFeignClient.class)
public class FeignConfig {
}
