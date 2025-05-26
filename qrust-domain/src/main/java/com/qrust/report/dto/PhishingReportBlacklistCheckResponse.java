package com.qrust.report.dto;

public record PhishingReportBlacklistCheckResponse(
        String domain,
        boolean isBlacklist,
        int reportCount
) {}
