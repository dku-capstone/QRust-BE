package com.qrust.external.google.infrastructure;

import com.qrust.common.infrastructure.google.GoogleSafeBrowsingAuthConfig;
import com.qrust.external.google.application.dto.request.GoogleSafeBrowsingRequest;
import com.qrust.external.google.application.dto.response.GoogleSafeBrowsingResponse;
import com.qrust.common.infrastructure.feign.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "safeBrowsingFeignClient",
        url = "${google.safe-browsing.api.base-url}",
        configuration = {FeignConfig.class, GoogleSafeBrowsingAuthConfig.class}
)
public interface GoogleSafeBrowsingFeignClient {
    @PostMapping(value = "/v4/threatMatches:find", produces = MediaType.APPLICATION_JSON_VALUE)
    GoogleSafeBrowsingResponse checkUrl(@RequestBody GoogleSafeBrowsingRequest request);
}
