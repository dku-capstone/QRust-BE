package com.qrust.qrcode.infrastructure.minio;

import com.qrust.common.infrastructure.s3.MinioProperties;
import com.qrust.qrcode.domain.service.QrCodeUpload;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.io.ByteArrayInputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrCodeMinioUploadImpl implements QrCodeUpload {

    private final MinioProperties minioProperties;
    private final MinioClient minioClient;

    @Override
    public String uploadQrCodeImage(byte[] qrCodeBytes) {
        try {
            // QR 코드 이미지의 파일 이름 생성
            String fileName = "qrust-" + UUID.randomUUID().toString().substring(0,8) + ".png";

            // MinIO에 QR 코드 이미지 업로드
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(fileName)
                            .stream(new ByteArrayInputStream(qrCodeBytes), qrCodeBytes.length, -1)
                            .contentType("image/png")
                            .build()
            );

            // 업로드된 QR 코드 이미지의 URL을 반환
            return minioProperties.getEndpoint() + "/" + minioProperties.getBucketName() + "/"
                    + fileName;

        } catch (Exception e) {
            throw new RuntimeException("QR 코드 이미지 업로드 실패", e);
        }
    }
}
