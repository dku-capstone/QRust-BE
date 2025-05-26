package com.qrust.report.application;

import com.qrust.report.domain.service.ReportUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlRecognizeFacade {
    private final ReportUrlService reportUrlService;

    public boolean isBlacklistUrl(String url){
        String domain = reportUrlService.extractDomain(url);
        return reportUrlService.findByUrl(domain).isPresent();
    }
}
