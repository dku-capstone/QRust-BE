package com.qrust.qrcode.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.qrcode.domain.entity.vo.QrCodeStatus;
import com.qrust.qrcode.domain.entity.vo.QrCodeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QrCodeTest {

    private QrCode qrCode;

    @BeforeEach
    void setUp() {
        QrCodeImage qrCodeImage = new QrCodeImage(null, "https://cdn.qrust.com/qrcodes/image123.png");

        qrCode = QrCode.builder()
                .userId(1L)
                .qrCodeData(new QrCodeData("https://example.com", "Example QR"))
                .qrCodeImage(qrCodeImage)
                .qrCodeType(QrCodeType.URL)
                .qrCodeStatus(QrCodeStatus.ACTIVE)
                .build();
    }

    @Test
    @DisplayName("QrCode 생성이 정상적으로 동작한다")
    void createQrCode_success() {
        assertThat(qrCode).isNotNull();
        assertThat(qrCode.getUserId()).isEqualTo(1L);
        assertThat(qrCode.getQrCodeData().getUrl()).isEqualTo("https://example.com");
        assertThat(qrCode.getQrCodeData().getTitle()).isEqualTo("Example QR");
        assertThat(qrCode.getQrCodeStatus()).isEqualTo(QrCodeStatus.ACTIVE);
        assertThat(qrCode.getQrCodeType()).isEqualTo(QrCodeType.URL);
        assertThat(qrCode.getQrCodeImage().getImageUrl()).isEqualTo("https://cdn.qrust.com/qrcodes/image123.png");
    }

    @Test
    @DisplayName("QrCode 업데이트 - 제목만 변경")
    void updateQrCode_onlyTitle() {
        qrCode.updateQrCode("New Title", null);

        assertThat(qrCode.getQrCodeData().getTitle()).isEqualTo("New Title");
        assertThat(qrCode.getQrCodeStatus()).isEqualTo(QrCodeStatus.ACTIVE); // unchanged
    }

    @Test
    @DisplayName("QrCode 업데이트 - 상태만 변경")
    void updateQrCode_onlyStatus() {
        qrCode.updateQrCode(null, QrCodeStatus.EXPIRED);

        assertThat(qrCode.getQrCodeStatus()).isEqualTo(QrCodeStatus.EXPIRED);
        assertThat(qrCode.getQrCodeData().getTitle()).isEqualTo("Example QR"); // unchanged
    }

    @Test
    @DisplayName("QrCode 업데이트 - 제목과 상태 모두 변경")
    void updateQrCode_titleAndStatus() {
        qrCode.updateQrCode("Updated Title", QrCodeStatus.EXPIRED);

        assertThat(qrCode.getQrCodeData().getTitle()).isEqualTo("Updated Title");
        assertThat(qrCode.getQrCodeStatus()).isEqualTo(QrCodeStatus.EXPIRED);
    }
}
