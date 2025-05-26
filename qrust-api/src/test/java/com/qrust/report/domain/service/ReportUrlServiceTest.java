package com.qrust.report.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.qrust.report.domain.entity.ReportUrl;
import com.qrust.report.domain.repository.ReportUrlRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReportUrlServiceTest {

    private ReportUrlRepository reportUrlRepository;
    private ReportUrlService reportUrlService;

    @BeforeEach
    void setUp() {
        reportUrlRepository = mock(ReportUrlRepository.class);
        reportUrlService = new ReportUrlService(reportUrlRepository);
    }

    @Test
    @DisplayName("정상적인 전체 URL이 들어왔을 때 도메인만 저장됨")
    void testUpsertWithFullUrl() {
        String fullUrl = "https://www.phishing.com/path/to/resource";
        String expectedDomain = "www.phishing.com";

        when(reportUrlRepository.findByUrl(expectedDomain)).thenReturn(Optional.empty());
        when(reportUrlRepository.save(any(ReportUrl.class))).thenReturn(ReportUrl.from(expectedDomain));

        ReportUrl saved = reportUrlService.upsert(fullUrl);

        assertEquals(expectedDomain, saved.getUrl());
        verify(reportUrlRepository).save(any(ReportUrl.class));
    }

    @Test
    @DisplayName("도메인만 들어와도 정상적으로 동작")
    void testUpsertWithDomainOnly() {
        String domainOnly = "example.com";

        when(reportUrlRepository.findByUrl("example.com")).thenReturn(Optional.empty());
        when(reportUrlRepository.save(any(ReportUrl.class))).thenReturn(ReportUrl.from("example.com"));

        ReportUrl saved = reportUrlService.upsert(domainOnly);

        assertEquals("example.com", saved.getUrl());
    }

    @Test
    @DisplayName("이미 존재하는 도메인인 경우 저장하지 않음")
    void testUpsertWhenDomainAlreadyExists() {
        String fullUrl = "http://test.com/page";
        ReportUrl existing = ReportUrl.from("test.com");

        when(reportUrlRepository.findByUrl("test.com")).thenReturn(Optional.of(existing));

        ReportUrl result = reportUrlService.upsert(fullUrl);

        assertEquals("test.com", result.getUrl());
        verify(reportUrlRepository, never()).save(any());
    }
}
