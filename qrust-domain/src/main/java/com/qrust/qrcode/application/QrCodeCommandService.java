package com.qrust.qrcode.application;

import com.qrust.qrcode.domain.entity.QrCode;
import com.qrust.qrcode.domain.entity.vo.QrCodeStatus;
import com.qrust.qrcode.domain.repository.QrCodeRepository;
import com.qrust.qrcode.dto.request.QrCodeUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QrCodeCommandService {
    private final QrCodeRepository qrCodeRepository;

    // 수정
    public void updateQrCode(QrCodeUpdateRequestDto dto, Long qrCodeId) {
        QrCode qrCode = getQrCode(qrCodeId);
        qrCode.updateQrCode(dto.title(), dto.status());
    }

    // 삭제
    public void deleteQrCode(Long qrCodeId) {
        QrCode qrCode = getQrCode(qrCodeId);
        qrCode.updateQrCode(null, QrCodeStatus.EXPIRED);
//        qrCodeRepository.deleteById(qrCodeId);
    }

    private QrCode getQrCode(Long qrCodeId) {
        return qrCodeRepository.findById(qrCodeId)
                .orElseThrow(() -> new IllegalArgumentException("QR 코드를 찾을 수 없습니다."));
    }
}
