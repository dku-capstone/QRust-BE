package com.qrust.qrcode.infrastructure.minio;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.qrcode.infrastructure.QrCodeTestTemplate;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public class MinIOIntTest extends QrCodeTestTemplate {

    @Autowired
    private QrCodeUpload qrCodeUploader;

    static final String BUCKET_NAME = "qrust-bucket";

    @Test
    void generateQrCodeAndUploadToMinio() throws IOException {
        // given
        QrCodeData qrCodeData = createTestQrCodeData();

        // when
        byte[] qrCodeImage = qrCodeGenerator.generateQrCode(qrCodeData);

        // MinIO 업로드
        String imageUrl = qrCodeUploader.uploadQrCodeImage(qrCodeImage);

        // then
        assertNotNull(imageUrl);
        assertTrue(imageUrl.contains("/qrust-bucket/"));
        System.out.println("✅ 업로드 성공 URL: " + imageUrl);
    }

    @Container
    static GenericContainer<?> minio = new GenericContainer<>("minio/minio:latest")
            .withExposedPorts(9000)
            .withEnv("MINIO_ROOT_USER", "minioadmin")
            .withEnv("MINIO_ROOT_PASSWORD", "minioadmin")
            .withCommand("server /data");

    @DynamicPropertySource
    static void setMinioProperties(DynamicPropertyRegistry registry) {
        String endpoint = "http://" + minio.getHost() + ":" + minio.getMappedPort(9000);
        registry.add("minio.endpoint", () -> endpoint);
        registry.add("minio.access-key", () -> "minioadmin");
        registry.add("minio.secret-key", () -> "minioadmin");
    }

    @BeforeAll
    static void setupBucket() throws Exception {
        String endpoint = "http://" + minio.getHost() + ":" + minio.getMappedPort(9000);

        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials("minioadmin", "minioadmin")
                .build();

        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
            System.out.println("✅ MinIO 버킷 생성 완료: " + BUCKET_NAME);
        } else {
            System.out.println("⚠️ MinIO 버킷 이미 존재함: " + BUCKET_NAME);
        }
    }


}
