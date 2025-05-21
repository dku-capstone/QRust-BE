package com.qrust.qrcode.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.qrust.qrcode.domain.entity.QrCode;
import com.qrust.qrcode.domain.entity.QrCodeImage;
import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.qrcode.domain.entity.vo.QrCodeStatus;
import com.qrust.qrcode.domain.repository.QrCodeRepository;
import com.qrust.qrcode.dto.request.QrCodeUpdateRequestDto;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QrCodeCommandServiceTest {

    private QrCodeRepository qrCodeRepository;
    private QrCodeCommandService qrCodeCommandService;

    private QrCode existingQrCode;

    @BeforeEach
    void setUp() {
        qrCodeRepository = mock(QrCodeRepository.class);
        qrCodeCommandService = new QrCodeCommandService(qrCodeRepository);

        existingQrCode = QrCode.builder()
                .userId(1L)
                .qrCodeData(new QrCodeData("https://test.com", "Original Title"))
                .qrCodeImage(new QrCodeImage(null, "https://cdn.qrust.com/qr.png"))
                .qrCodeStatus(QrCodeStatus.ACTIVE)
                .build();
    }

    @Test
    @DisplayName("QR 코드 수정 - 제목과 상태를 업데이트한다")
    void updateQrCode_success() {
        // given
        Long qrCodeId = 10L;
        QrCodeUpdateRequestDto dto = new QrCodeUpdateRequestDto("Updated Title", QrCodeStatus.EXPIRED);

        when(qrCodeRepository.findById(qrCodeId)).thenReturn(Optional.of(existingQrCode));

        // when
        qrCodeCommandService.updateQrCode(dto, qrCodeId);

        // then
        assertThat(existingQrCode.getQrCodeData().getTitle()).isEqualTo("Updated Title");
        assertThat(existingQrCode.getQrCodeStatus()).isEqualTo(QrCodeStatus.EXPIRED);
    }

    @Test
    @DisplayName("QR 코드 삭제 - 상태를 EXPIRED로 변경한다")
    void deleteQrCode_marksAsExpired() {
        // given
        Long qrCodeId = 11L;
        when(qrCodeRepository.findById(qrCodeId)).thenReturn(Optional.of(existingQrCode));

        // when
        qrCodeCommandService.deleteQrCode(qrCodeId);

        // then
        assertThat(existingQrCode.getQrCodeStatus()).isEqualTo(QrCodeStatus.EXPIRED);
    }

    @Test
    @DisplayName("QR 코드가 존재하지 않을 경우 예외를 던진다")
    void getQrCode_notFound_throwsException() {
        // given
        Long qrCodeId = 99L;
        when(qrCodeRepository.findById(qrCodeId)).thenReturn(Optional.empty());

        // then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> qrCodeCommandService.updateQrCode(new QrCodeUpdateRequestDto("title", QrCodeStatus.ACTIVE), qrCodeId));
    }
}
