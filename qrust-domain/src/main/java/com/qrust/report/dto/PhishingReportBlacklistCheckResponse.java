package com.qrust.report.dto;

public record PhishingReportBlacklistCheckResponse(
        String domain,
        boolean is_blacklist,
        int reportCount
) {}
