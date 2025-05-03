package com.qrust.controller.external.google.dto.request;

import java.util.List;
import lombok.Data;

@Data
public class GoogleSafeBrowsingRequest {

    private Client client;
    private ThreatInfo threatInfo;

    @Data
    public static class Client {
        private String clientId;
        private String clientVersion;
    }

    @Data
    public static class ThreatInfo {
        private List<String> threatTypes;
        private List<String> platformTypes;
        private List<String> threatEntryTypes;
        private List<ThreatEntry> threatEntries;

        @Data
        public static class ThreatEntry {
            private String url;
        }
    }
}
