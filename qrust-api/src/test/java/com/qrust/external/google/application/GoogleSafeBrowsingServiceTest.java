package com.qrust.external.google.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.qrust.external.google.application.dto.request.GoogleSafeBrowsingRequest;
import com.qrust.external.google.application.dto.response.GoogleSafeBrowsingResponse;
import com.qrust.external.google.application.dto.response.GoogleSafeBrowsingResponse.ThreatMatch;
import com.qrust.external.google.infrastructure.GoogleSafeBrowsingFeignClient;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoogleSafeBrowsingServiceTest {

    private GoogleSafeBrowsingFeignClient feignClient;
    private GoogleSafeBrowsingService service;

    @BeforeEach
    void 초기_세팅() {
        feignClient = mock(GoogleSafeBrowsingFeignClient.class);
        service = new GoogleSafeBrowsingService(feignClient);
    }

    @Test
    void 위협_감지시_true_반환한다() {
        // given
        String url = "http://malicious.com";
        GoogleSafeBrowsingResponse response = new GoogleSafeBrowsingResponse();
        response.setMatches(List.of(new ThreatMatch())); // 비어있지 않게

        when(feignClient.checkUrl(any(GoogleSafeBrowsingRequest.class))).thenReturn(response);

        // when
        boolean result = service.isUrlDangerous(url);

        // then
        assertThat(result).isTrue();
        verify(feignClient, times(1)).checkUrl(any());
    }

    @Test
    void 위협_없을때_false_반환한다() {
        // given
        String url = "http://example.com";
        GoogleSafeBrowsingResponse response = new GoogleSafeBrowsingResponse();
        response.setMatches(Collections.emptyList());

        when(feignClient.checkUrl(any(GoogleSafeBrowsingRequest.class))).thenReturn(response);

        // when
        boolean result = service.isUrlDangerous(url);

        // then
        assertThat(result).isFalse();
        verify(feignClient, times(1)).checkUrl(any());
    }

    @Test
    void 응답이_null일_경우_false_반환한다() {
        // given
        String url = "http://safe.com";
        when(feignClient.checkUrl(any(GoogleSafeBrowsingRequest.class))).thenReturn(null);

        // when
        boolean result = service.isUrlDangerous(url);

        // then
        assertThat(result).isFalse();
    }
}
