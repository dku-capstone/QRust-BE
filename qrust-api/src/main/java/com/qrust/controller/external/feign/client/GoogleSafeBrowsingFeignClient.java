package com.qrust.controller.external.feign.client;

import com.qrust.controller.external.feign.config.FeignConfig;
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
    SafeBrowsingResponse checkUrl(@RequestBody SafeBrowsingRequest request);
}
