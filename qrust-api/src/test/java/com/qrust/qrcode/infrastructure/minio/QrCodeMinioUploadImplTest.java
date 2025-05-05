package com.qrust.qrcode.infrastructure.minio;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.qrcode.infrastructure.QrCodeTestTemplate;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Tag("minio")
class QrCodeMinioUploadImplTest extends QrCodeTestTemplate {

    @Autowired
    private QrCodeMinioUploadImpl qrCodeUploader;

    @Test
    void generateQrCodeAndUploadToMinio() throws IOException {
        // given
        QrCodeData qrCodeData = createTestQrCodeData();

        // when
        byte[] qrCodeImage = qrCodeGenerator.generateQrCode(qrCodeData);

        // 로컬 저장
        Path outputPath = Paths.get("src/test/resources/generated_test_qr.png");
        Files.write(outputPath, qrCodeImage);

        // MinIO 업로드
        String imageUrl = qrCodeUploader.uploadQrCodeImage(qrCodeImage);

        // then
        assertNotNull(imageUrl);
        assertTrue(imageUrl.contains("/qrust-bucket/"));
        System.out.println("✅ 업로드 성공 URL: " + imageUrl);
    }

}
