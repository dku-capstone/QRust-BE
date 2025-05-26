package com.qrust.qrcode.application;

import static com.qrust.exception.qrcode.ErrorMessages.QR_NOT_FOUND;

import com.qrust.dto.PageResponse;
import com.qrust.exception.CustomException;
import com.qrust.exception.error.ErrorCode;
import com.qrust.qrcode.domain.entity.QrCode;
import com.qrust.qrcode.domain.repository.QrCodeQueryRepository;
import com.qrust.qrcode.domain.repository.QrCodeRepository;
import com.qrust.qrcode.dto.request.QrCodeSearchRequestDto;
import com.qrust.qrcode.dto.response.QrCodeListResponseDto;
import com.qrust.qrcode.dto.response.QrCodeResponseDto;
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
                        qrCode.getCreatedAt().toLocalDate(),
                        qrCode.getQrCodeData().getUrl(),
                        qrCode.getQrCodeStatus()
                ));
        return PageResponse.from(result);
    }

    public QrCodeResponseDto getQrCode(Long id) {

        QrCode qrCode = qrCodeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_END_POINT, QR_NOT_FOUND));

        return new QrCodeResponseDto(
                qrCode.getId(),
                qrCode.getQrCodeStatus(),
                qrCode.getQrCodeImage().getImageUrl(),
                qrCode.getQrCodeData().getTitle(),
                qrCode.getCreatedAt().toLocalDate(),
                qrCode.getQrCodeData().getUrl()
        );
    }

    public PageResponse<QrCodeListResponseDto> searchQrCode(QrCodeSearchRequestDto dto, Pageable pageable) {
        Page<QrCodeListResponseDto> result = qrCodeQueryRepository.searchQrCode(dto, pageable);

        return PageResponse.from(result);
    }
}