package com.qrust.report.domain.service;

import static com.qrust.exception.error.ErrorCode.INVALID_INPUT_VALUE;
import static com.qrust.exception.report.ErrorMessages.REPORT_URL_INVALID;
import static com.qrust.exception.report.ErrorMessages.REPORT_URL_NOT_EXIST;

import com.qrust.exception.CustomException;
import com.qrust.report.domain.entity.ReportUrl;
import com.qrust.report.domain.repository.ReportUrlRepository;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportUrlService {
    private final ReportUrlRepository reportUrlRepository;

    @Transactional
    public ReportUrl upsert(String url) {
        String domain = extractDomain(url);
        return findByUrl(domain)
                .orElseGet(() -> reportUrlRepository.save(ReportUrl.from(url)));
    }

    @Transactional
    public void increaseReportCount(String url) {
        ReportUrl reportUrl = getByUrl(url);
        reportUrl.incrementReportCount();
    }

    @Transactional(readOnly = true)
    public Optional<ReportUrl> findByUrl(String url) {
        return reportUrlRepository.findByUrl(url);
    }

    @Transactional(readOnly = true)
    public ReportUrl getByUrl(String url) {
        return findByUrl(url)
                .orElseThrow(() -> new CustomException(INVALID_INPUT_VALUE, REPORT_URL_NOT_EXIST));
    }

    private String extractDomain(String fullUrl) {
        try {
            if (!fullUrl.startsWith("http://") && !fullUrl.startsWith("https://")) {
                fullUrl = "http://" + fullUrl;
            }
            URL url = new URL(fullUrl);
            return url.getHost();
        } catch (MalformedURLException e) {
            throw new CustomException(INVALID_INPUT_VALUE, REPORT_URL_INVALID);
        }
    }
}
