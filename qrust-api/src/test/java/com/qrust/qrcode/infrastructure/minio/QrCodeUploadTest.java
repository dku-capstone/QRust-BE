package com.qrust.qrcode.infrastructure.minio;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.qrcode.infrastructure.QrCodeTestTemplate;
import java.io.IOException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Tag("minio")
class QrCodeUploadTest extends QrCodeTestTemplate {

    @Autowired
    private QrCodeUpload qrCodeUploader;

    @Test
    void generateQrCodeAndUploadToMinio() throws IOException {
        // given
        QrCodeData qrCodeData = createTestQrCodeData();

        // when
        byte[] qrCodeImage = qrCodeGenerator.generateQrCode(qrCodeData);

//        // 로컬 저장
//        Path outputPath = Paths.get("src/test/resources/generated_test_qr.png");
//        Files.write(outputPath, qrCodeImage);

        // MinIO 업로드
        String imageUrl = qrCodeUploader.uploadQrCodeImage(qrCodeImage);

        // then
        assertNotNull(imageUrl);
        assertTrue(imageUrl.contains("/qrust-bucket/"));
        System.out.println("✅ 업로드 성공 URL: " + imageUrl);
    }

}
