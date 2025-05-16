package com.qrust.external.ai.infrastructure;

import com.qrust.common.infrastructure.feign.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "aiModelClient",
        url = "${ai.model.base-url}",
        configuration = {FeignConfig.class}
)
public interface AiModelFeignClient {

    @PostMapping(value = "/api/v1/ai/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
    AiModelResponse verifyUrl(@RequestBody AiModelRequest request);
}
