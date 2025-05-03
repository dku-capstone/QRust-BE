package com.qrust.controller.external.feign.config;

import com.qrust.controller.external.feign.client.GoogleSafeBrowsingFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = GoogleSafeBrowsingFeignClient.class)
public class FeignConfig {
}
