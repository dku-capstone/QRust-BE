package com.qrust.external.ai.infrastructure;

import com.qrust.common.infrastructure.feign.FeignConfig;
import com.qrust.external.ai.application.dto.request.AiModelRequest;
import com.qrust.external.ai.application.dto.response.AiModelResponse;
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

    /****
     * Sends a POST request to the external AI model service to verify the provided URL.
     *
     * @param request the request payload containing URL and related data for verification
     * @return the response from the AI model service with verification results
     */
    @PostMapping(value = "/api/v1/ai/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
    AiModelResponse verifyUrl(@RequestBody AiModelRequest request);
}
