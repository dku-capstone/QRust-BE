package com.qrust.external.google.application;

import static com.qrust.exception.error.ErrorCode.INVALID_INPUT_VALUE;
import static com.qrust.exception.external.google.ErrorMessages.GOOGLE_API_FAILED;
import static com.qrust.exception.external.google.ErrorMessages.GOOGLE_URL_EMPTY;
import static com.qrust.exception.external.google.ErrorMessages.GOOGLE_URL_INVALID;
import static com.qrust.exception.report.ErrorMessages.REPORT_URL_INVALID;

import com.qrust.exception.CustomException;
import com.qrust.external.google.application.dto.request.GoogleSafeBrowsingRequest;
import com.qrust.external.google.application.dto.response.GoogleSafeBrowsingResponse;
import com.qrust.external.google.infrastructure.GoogleSafeBrowsingFeignClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoogleSafeBrowsingService {

    private static final String CLIENT_ID = "qrust-app";
    private static final String CLIENT_VERSION = "1.0";

    private static final List<String> THREAT_TYPES = List.of("MALWARE", "SOCIAL_ENGINEERING", "UNWANTED_SOFTWARE");
    private static final List<String> PLATFORM_TYPES = List.of("ANY_PLATFORM");
    private static final List<String> THREAT_ENTRY_TYPES = List.of("URL");

    private final GoogleSafeBrowsingFeignClient googleSafeBrowsingFeignClient;

    public boolean isUrlDangerous(String url) {
        if (url == null || url.isEmpty()) {
            log.warn("입력된 URL이 비어 있습니다.");
            throw new CustomException(INVALID_INPUT_VALUE, GOOGLE_URL_EMPTY);
        }

        String normalizedUrl = normalizeUrl(url);

        if (!isValidUrl(normalizedUrl)) {
            log.warn("유효하지 않은 URL 형식: {}", url);
            throw new CustomException(INVALID_INPUT_VALUE, GOOGLE_URL_INVALID);
        }

        try {
            GoogleSafeBrowsingRequest request = buildRequest(url);
            GoogleSafeBrowsingResponse response = googleSafeBrowsingFeignClient.checkUrl(request);
            return hasThreatMatches(response);
        } catch (Exception e) {
            log.error("Google Safe Browsing API 호출 중 오류 발생: {}", e.getMessage(), e);
            throw new CustomException(INVALID_INPUT_VALUE, GOOGLE_API_FAILED);
        }
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

    private boolean isValidUrl(String url) {
        try {
            new java.net.URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String normalizeUrl(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "https://" + url;
        }
        return url;
    }

}
