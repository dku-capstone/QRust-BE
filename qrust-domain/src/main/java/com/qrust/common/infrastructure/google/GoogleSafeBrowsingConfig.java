package com.qrust.common.infrastructure.google;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleSafeBrowsingConfig {

    @Value("${google.safe-browsing.api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}
