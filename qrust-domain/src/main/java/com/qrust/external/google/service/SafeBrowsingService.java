package com.qrust.external.google.service;

import com.qrust.external.google.dto.request.GoogleSafeBrowsingRequest;
import com.qrust.external.google.dto.response.GoogleSafeBrowsingResponse;
import com.qrust.external.google.feign.client.GoogleSafeBrowsingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SafeBrowsingService {

    private static final String CLIENT_ID = "qrust-app";
    private static final String CLIENT_VERSION = "1.0";

    private static final List<String> THREAT_TYPES = List.of("MALWARE", "SOCIAL_ENGINEERING", "UNWANTED_SOFTWARE");
    private static final List<String> PLATFORM_TYPES = List.of("ANY_PLATFORM");
    private static final List<String> THREAT_ENTRY_TYPES = List.of("URL");

    private final GoogleSafeBrowsingFeignClient googleSafeBrowsingFeignClient;

    public boolean isUrlDangerous(String url) {
        GoogleSafeBrowsingRequest request = buildRequest(url);
        GoogleSafeBrowsingResponse response = googleSafeBrowsingFeignClient.checkUrl(request);
        return hasThreatMatches(response);
    }

    private GoogleSafeBrowsingRequest buildRequest(String url) {
        var client = new GoogleSafeBrowsingRequest.Client();
        client.setClientId(CLIENT_ID);
        client.setClientVersion(CLIENT_VERSION);

        var threatEntry = new GoogleSafeBrowsingRequest.ThreatInfo.ThreatEntry();
        threatEntry.setUrl(url);

        var threatInfo = new GoogleSafeBrowsingRequest.ThreatInfo();
        threatInfo.setThreatTypes(THREAT_TYPES);
        threatInfo.setPlatformTypes(PLATFORM_TYPES);
        threatInfo.setThreatEntryTypes(THREAT_ENTRY_TYPES);
        threatInfo.setThreatEntries(List.of(threatEntry));

        var request = new GoogleSafeBrowsingRequest();
        request.setClient(client);
        request.setThreatInfo(threatInfo);

        return request;
    }

    private boolean hasThreatMatches(GoogleSafeBrowsingResponse response) {
        return response != null && response.getMatches() != null && !response.getMatches().isEmpty();
    }
}
