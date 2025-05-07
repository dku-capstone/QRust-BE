package com.qrust.qrcode.application;

import com.qrust.common.dto.PageResponse;
import com.qrust.qrcode.domain.entity.QrCode;
import com.qrust.qrcode.domain.repository.QrCodeQueryRepository;
import com.qrust.qrcode.domain.repository.QrCodeRepository;
import com.qrust.qrcode.dto.response.QrCodeListResponseDto;
import com.qrust.qrcode.dto.response.QrCodeResponseDto;
import com.qrust.qrcode.dto.request.QrCodeSearchRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QrCodeQueryService {

    private final QrCodeQueryRepository qrCodeQueryRepository;
    private final QrCodeRepository qrCodeRepository;

    public PageResponse<QrCodeListResponseDto> getQrCodes(Long userId, Pageable pageable) {

        Page<QrCodeListResponseDto> result = qrCodeRepository.findAllByUserId(userId, pageable)
                .map(qrCode -> new QrCodeListResponseDto(
                        qrCode.getId(),
                        qrCode.getQrCodeImage().getImageUrl(),
                        qrCode.getQrCodeData().getTitle(),
                        qrCode.getCreatedAt(),
                        qrCode.getQrCodeData().getUrl()
                ));
        return PageResponse.from(result);
    }

    public QrCodeResponseDto getQrCode(Long id) {

        //TODO
        // 커스텀 예외 처리
        QrCode qrCode = qrCodeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("QR 코드를 찾을 수 없습니다."));

        return new QrCodeResponseDto(
                qrCode.getQrCodeImage().getImageUrl(),
                qrCode.getQrCodeData().getTitle(),
                qrCode.getCreatedAt(),
                qrCode.getQrCodeData().getUrl()
        );
    }

    public PageResponse<QrCodeListResponseDto> searchQrCode(QrCodeSearchRequestDto dto, Pageable pageable) {
        Page<QrCodeListResponseDto> result = qrCodeQueryRepository.searchQrCode(dto, pageable);

        return PageResponse.from(result);
    }
}