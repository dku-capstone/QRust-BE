package com.qrust.report.domain.repository;

import com.qrust.report.domain.entity.ReportUrl;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportUrlRepository extends JpaRepository<ReportUrl, Long> {
    Optional<ReportUrl> findByUrl(String url);
}
