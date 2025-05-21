package com.qrust.qrcode.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.qrust.qrcode.domain.entity.QrCode;
import com.qrust.qrcode.domain.entity.QrCodeImage;
import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.qrcode.domain.repository.QrCodeImageRepository;
import com.qrust.qrcode.domain.repository.QrCodeRepository;
import com.qrust.qrcode.domain.service.QrCodeGenerator;
import com.qrust.qrcode.infrastructure.minio.QrCodeUpload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class QrCodeGeneratorServiceTest {

    private QrCodeGenerator qrCodeGenerator;
    private QrCodeUpload qrCodeUpload;
    private QrCodeRepository qrCodeRepository;
    private QrCodeImageRepository qrCodeImageRepository;
    private QrCodeGeneratorService qrCodeGeneratorService;

    @BeforeEach
    void setUp() {
        qrCodeGenerator = mock(QrCodeGenerator.class);
        qrCodeUpload = mock(QrCodeUpload.class);
        qrCodeRepository = mock(QrCodeRepository.class);
        qrCodeImageRepository = mock(QrCodeImageRepository.class);

        qrCodeGeneratorService = new QrCodeGeneratorService(
                qrCodeGenerator,
                qrCodeUpload,
                qrCodeRepository,
                qrCodeImageRepository
        );
    }

    @Test
    @DisplayName("QR 코드 생성 요청 시, 이미지 URL을 반환하고 저장 로직이 정상 작동한다")
    void generateQrCode_success() {
        // given
        QrCodeData qrCodeData = new QrCodeData("https://test.com", "Test QR");
        Long userId = 123L;
        byte[] dummyQrBytes = new byte[]{1, 2, 3};
        String dummyImageUrl = "https://cdn.qrust.com/bucket/qrust-1234.png";

        when(qrCodeGenerator.generateQrCode(qrCodeData)).thenReturn(dummyQrBytes);
        when(qrCodeUpload.uploadQrCodeImage(dummyQrBytes)).thenReturn(dummyImageUrl);

        // when
        String resultUrl = qrCodeGeneratorService.generateQrCode(qrCodeData, userId);

        // then
        assertThat(resultUrl).isEqualTo(dummyImageUrl);

        verify(qrCodeGenerator).generateQrCode(qrCodeData);
        verify(qrCodeUpload).uploadQrCodeImage(dummyQrBytes);

        ArgumentCaptor<QrCodeImage> imageCaptor = ArgumentCaptor.forClass(QrCodeImage.class);
        verify(qrCodeImageRepository).save(imageCaptor.capture());
        assertThat(imageCaptor.getValue().getImageUrl()).isEqualTo(dummyImageUrl);

        ArgumentCaptor<QrCode> qrCaptor = ArgumentCaptor.forClass(QrCode.class);
        verify(qrCodeRepository).save(qrCaptor.capture());
        assertThat(qrCaptor.getValue().getUserId()).isEqualTo(userId);
        assertThat(qrCaptor.getValue().getQrCodeData()).isEqualTo(qrCodeData);
    }
}
