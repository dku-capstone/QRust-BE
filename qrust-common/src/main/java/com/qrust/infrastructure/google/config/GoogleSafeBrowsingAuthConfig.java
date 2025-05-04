package com.qrust.infrastructure.google.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleSafeBrowsingAuthConfig implements RequestInterceptor {

    private static final String QUERY_PARAM_NAME = "key";

    private final GoogleSafeBrowsingConfig config;

    @Autowired
    public GoogleSafeBrowsingAuthConfig(GoogleSafeBrowsingConfig config) {
        this.config = config;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.query(QUERY_PARAM_NAME, config.getApiKey());
    }
}
