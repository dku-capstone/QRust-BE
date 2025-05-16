package com.qrust.external.ai.application;

import com.qrust.external.ai.application.dto.request.AiModelRequest;
import com.qrust.external.ai.application.dto.response.AiModelResponse;
import com.qrust.external.ai.infrastructure.AiModelFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AiModelUrlVerifyService {

    private final AiModelFeignClient aiModelFeignClient;

    /**
     * Verifies the given URL using an external AI model and returns the verification result.
     *
     * @param url the URL to be verified
     * @return the integer result from the AI model indicating the verification outcome
     */
    public int verifyUrl(String url) {
        AiModelRequest request = new AiModelRequest(url);
        AiModelResponse response = aiModelFeignClient.verifyUrl(request);
        return response.result();
    }
}
