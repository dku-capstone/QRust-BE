package com.qrust.qrcode.application;

import com.qrust.qrcode.domain.entity.QrCode;
import com.qrust.qrcode.domain.entity.QrCodeImage;
import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.qrcode.domain.repository.QrCodeImageRepository;
import com.qrust.qrcode.domain.repository.QrCodeRepository;
import com.qrust.qrcode.domain.service.QrCodeGenerator;
import com.qrust.qrcode.infrastructure.minio.QrCodeUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QrCodeGeneratorService {

    private final QrCodeGenerator qrCodeGenerator;
    private final QrCodeUpload qrCodeUpload;
    private final QrCodeRepository qrCodeRepository;
    private final QrCodeImageRepository qrCodeImageRepository;

    @Transactional
    public String generateQrCode(QrCodeData qrCodeData, Long userId) {

        //TODO
        // Title 중복 검증

        // 보안 QR 코드 생성
        byte[] qrCodeBytes = qrCodeGenerator.generateQrCode(qrCodeData);

        // QR 코드 이미지 minio 업로드
        String qrCodeImageUrl = qrCodeUpload.uploadQrCodeImage(qrCodeBytes);

        QrCodeImage qrCodeImage = new QrCodeImage(null, qrCodeImageUrl);

        QrCode qrCode = QrCode.builder()
                .userId(userId)
                .qrCodeData(qrCodeData)
                .qrCodeImage(qrCodeImage)
                .build();


        qrCodeImageRepository.save(qrCodeImage);
        qrCodeRepository.save(qrCode);

        return qrCodeImageUrl;
    }
}
