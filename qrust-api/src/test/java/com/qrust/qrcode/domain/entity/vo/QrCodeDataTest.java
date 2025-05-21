package com.qrust.qrcode.domain.entity.vo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QrCodeDataTest {

    @Test
    @DisplayName("QrCodeData 생성 테스트")
    void createQrCodeData() {
        QrCodeData data = new QrCodeData("https://example.com", "Example Title");

        assertThat(data.getUrl()).isEqualTo("https://example.com");
        assertThat(data.getTitle()).isEqualTo("Example Title");
    }

    @Test
    @DisplayName("QrCodeData equals & hashCode 동작 테스트")
    void equalsAndHashCode() {
        QrCodeData data1 = new QrCodeData("https://site.com", "My QR");
        QrCodeData data2 = new QrCodeData("https://site.com", "My QR");

        assertThat(data1).isEqualTo(data2);
        assertThat(data1.hashCode()).isEqualTo(data2.hashCode());
    }

    @Test
    @DisplayName("QrCodeData 값이 다르면 equals는 false를 반환한다")
    void inequalityTest() {
        QrCodeData data1 = new QrCodeData("https://site.com", "QR A");
        QrCodeData data2 = new QrCodeData("https://site.com", "QR B");

        assertThat(data1).isNotEqualTo(data2);
    }
}
