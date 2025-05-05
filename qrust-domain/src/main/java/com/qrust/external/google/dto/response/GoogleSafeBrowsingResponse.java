package com.qrust.external.google.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class GoogleSafeBrowsingResponse {

    private List<ThreatMatch> matches;

    @Data
    public static class ThreatMatch {
        private Threat threat;
        private String threatType;
        private String platformType;
        private String threatEntryType;
        private ThreatEntryMetadata threatEntryMetadata;

        @Data
        public static class Threat {
            private String url;
        }

        @Data
        public static class ThreatEntryMetadata {
            private List<Entry> entries;

            @Data
            public static class Entry {
                private String key;
                private String value;
            }
        }
    }
}
