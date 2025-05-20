package com.qrust.qrcode.infrastructure.minio;

import static com.qrust.exception.qrcode.ErrorMessages.IMAGE_UPLOAD_FAIL;

import com.qrust.common.infrastructure.s3.MinioProperties;
import com.qrust.exception.CustomException;
import com.qrust.exception.error.ErrorCode;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.io.ByteArrayInputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrCodeUpload {

    private final MinioProperties minioProperties;
    private final MinioClient minioClient;

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
            return minioProperties.getPrefix() + "/" + minioProperties.getBucketName() + "/"
                    + fileName;

        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, IMAGE_UPLOAD_FAIL);
        }
    }
}
