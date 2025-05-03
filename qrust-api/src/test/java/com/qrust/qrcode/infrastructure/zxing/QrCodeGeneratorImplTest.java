package com.qrust.qrcode.infrastructure.zxing;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.qrcode.infrastructure.QrCodeTestTemplate;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class QrCodeGeneratorImplTest extends QrCodeTestTemplate {


    @Test
    void generateQrCode() throws IOException {
        //given
        QrCodeData qrCodeData = createTestQrCodeData();

        //when
        byte[] qrCodeImage = qrCodeGenerator.generateQrCode(qrCodeData);

        Path outputPath = Paths.get("src/test/resources/generated_qr.png");
        Files.write(outputPath, qrCodeImage);

        //then
        assertNotNull(qrCodeImage);
        assertTrue(qrCodeImage.length > 0);
    }
}
