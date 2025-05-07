package com.qrust.qrcode.infrastructure.zxing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.qrcode.infrastructure.QrCodeTestTemplate;
import org.junit.jupiter.api.Test;

class QrCodeDecoderImplTest extends QrCodeTestTemplate {

    @Test
    void generateAndDecodeQrCode_shouldReturnOriginalData() throws Exception {
        //given
        QrCodeData original = createTestQrCodeData();

        byte[] byteQrCode = qrCodeGenerator.generateQrCode(original);

        //when
        QrCodeData decoded = qrCodeDecoder.decodeQrCodeData(byteQrCode);

        //then
        assertNotNull(decoded);
        assertEquals(original.getUrl(), decoded.getUrl());
        assertEquals(original.getTitle(), decoded.getTitle());
    }
}