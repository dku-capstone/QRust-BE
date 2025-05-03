package com.qrust.qrcode.application;

import com.qrust.qrcode.domain.entity.QrCode;
import com.qrust.qrcode.domain.entity.QrCodeImage;
import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.qrcode.domain.service.QrCodeGenerator;
import com.qrust.qrcode.domain.service.QrCodeUpload;
import com.qrust.qrcode.infrastructure.jpa.QrCodeImageRepository;
import com.qrust.qrcode.infrastructure.jpa.QrCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QrCodeGeneratorService {

    private final QrCodeGenerator qrCodeGenerator;
    private final QrCodeUpload qrCodeUpload;
    private final QrCodeRepository qrCodeRepository;
    private final QrCodeImageRepository qrCodeImageRepository;

    @Transactional
    public String generateQrCode(QrCodeData qrCodeData, Long userId) {

        // 보안 QR 코드 생성
        byte[] qrCodeBytes = qrCodeGenerator.generateQrCode(qrCodeData);

        // QR 코드 이미지 minio 업로드
        String qrCodeImageUrl = qrCodeUpload.uploadQrCodeImage(qrCodeBytes);

        //TODO
        // User 연동

        QrCodeImage qrCodeImage = new QrCodeImage(null, qrCodeImageUrl);
        QrCode qrCode = new QrCode(null, userId, qrCodeData, qrCodeImage, false);

        qrCodeImageRepository.save(qrCodeImage);
        qrCodeRepository.save(qrCode);

        return qrCodeImageUrl;
    }
}
