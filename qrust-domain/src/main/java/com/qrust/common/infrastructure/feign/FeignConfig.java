package com.qrust.common.infrastructure.feign;

import com.qrust.external.ai.infrastructure.AiModelFeignClient;
import com.qrust.external.google.infrastructure.GoogleSafeBrowsingFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {
        GoogleSafeBrowsingFeignClient.class,
        AiModelFeignClient.class
})
public class FeignConfig {
}
