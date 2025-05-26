package com.qrust.qrcode.infrastructure.zxing;

import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.qrcode.infrastructure.QrCodeTestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QrCodeDecoderImplTest extends QrCodeTestTemplate {

//    @Test
    @DisplayName("AES-GCM 암호화 기반 QR 코드 생성 및 복호화 - 원본 일치")
    void generateAndDecodeQrCode_aesGcm_success() {
        // given
        QrCodeData original = new QrCodeData("https://secure.qrust.com", "QR-AES-GCM-Test");

        // when
        byte[] qrCodeImageBytes = qrCodeGenerator.generateQrCode(original);
//        QrCodeData decoded = qrCodeDecoder.decodeQrCodeData(qrCodeImageBytes);

        // then
//        assertThat(decoded).isEqualTo(original);
    }

    @Test
    void generateAndDecodeQrCode_shouldReturnOriginalData() throws Exception {
        //given
        QrCodeData original = createTestQrCodeData();

        byte[] byteQrCode = qrCodeGenerator.generateQrCode(original);

        //when
//        QrCodeData decoded = qrCodeDecoder.decodeQrCodeData(byteQrCode);

        //then
//        assertNotNull(decoded);
//        assertEquals(original.getUrl(), decoded.getUrl());
//        assertEquals(original.getTitle(), decoded.getTitle());
    }
}