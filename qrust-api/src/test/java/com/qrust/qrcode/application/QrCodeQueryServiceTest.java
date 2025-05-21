package com.qrust.qrcode.application;

import static com.qrust.exception.qrcode.ErrorMessages.QR_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.qrust.exception.CustomException;
import com.qrust.exception.error.ErrorCode;
import com.qrust.qrcode.domain.entity.QrCode;
import com.qrust.qrcode.domain.entity.QrCodeImage;
import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.qrcode.domain.entity.vo.QrCodeStatus;
import com.qrust.qrcode.domain.entity.vo.QrCodeType;
import com.qrust.qrcode.domain.repository.QrCodeQueryRepository;
import com.qrust.qrcode.domain.repository.QrCodeRepository;
import com.qrust.qrcode.dto.request.QrCodeSearchRequestDto;
import com.qrust.qrcode.dto.response.QrCodeListResponseDto;
import com.qrust.qrcode.dto.response.QrCodeResponseDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

class QrCodeQueryServiceTest {

    private QrCodeRepository qrCodeRepository;
    private QrCodeQueryRepository qrCodeQueryRepository;
    private QrCodeQueryService qrCodeQueryService;

    @BeforeEach
    void setUp() {
        qrCodeRepository = mock(QrCodeRepository.class);
        qrCodeQueryRepository = mock(QrCodeQueryRepository.class);
        qrCodeQueryService = new QrCodeQueryService(qrCodeQueryRepository, qrCodeRepository);
    }



    @Test
    @DisplayName("getQrCode - ID로 단건 조회 성공")
    void getQrCode_success() {
        Long id = 99L;

        QrCode qrCode = QrCode.builder()
                .userId(1L)
                .qrCodeImage(new QrCodeImage(null, "https://cdn.qrust.com/qrcode.png"))
                .qrCodeData(new QrCodeData("https://site.com", "개별 QR"))
                .qrCodeStatus(QrCodeStatus.ACTIVE)
                .build();

        // 👉 createdAt 필드 강제 주입
        ReflectionTestUtils.setField(qrCode, "createdAt", LocalDateTime.of(2025, 4, 15, 10, 30));
        ReflectionTestUtils.setField(qrCode, "id", id); // ID도 필요하면 주입 가능

        when(qrCodeRepository.findById(id)).thenReturn(Optional.of(qrCode));

        QrCodeResponseDto dto = qrCodeQueryService.getQrCode(id);

        assertThat(dto.qrCodeId()).isEqualTo(id);
        assertThat(dto.status()).isEqualTo(QrCodeStatus.ACTIVE);
        assertThat(dto.title()).isEqualTo("개별 QR");
        assertThat(dto.qrCodeImageUrl()).isEqualTo("https://cdn.qrust.com/qrcode.png");
        assertThat(dto.url()).isEqualTo("https://site.com");
        assertThat(dto.createdAt()).isEqualTo(LocalDate.of(2025, 4, 15)); // 이 줄도 이제 안전!
    }

    @Test
    @DisplayName("getQrCode - 존재하지 않는 ID는 예외 발생")
    void getQrCode_notFound() {
        Long id = 999L;
        when(qrCodeRepository.findById(id)).thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class,
                () -> qrCodeQueryService.getQrCode(id));

        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_END_POINT);
        assertThat(ex.getMessage()).isEqualTo(QR_NOT_FOUND);
    }

    @Test
    @DisplayName("searchQrCode - 검색 조건으로 조회 성공")
    void searchQrCode_success() {
        QrCodeSearchRequestDto dto = new QrCodeSearchRequestDto(
                "테스트", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), QrCodeType.URL, QrCodeStatus.ACTIVE
        );
        Pageable pageable = PageRequest.of(0, 5);

        QrCodeListResponseDto responseDto = new QrCodeListResponseDto(
                5L,
                "https://cdn.qrust.com/qr.png",
                "테스트 결과",
                LocalDate.of(2025, 3, 20),
                "https://example.com"
        );

        when(qrCodeQueryRepository.searchQrCode(dto, pageable))
                .thenReturn(new PageImpl<>(List.of(responseDto), pageable, 1));

        var result = qrCodeQueryService.searchQrCode(dto, pageable);

        List<QrCodeListResponseDto> content = result.content();
        assertThat(content).hasSize(1);
        assertThat(content.get(0).title()).isEqualTo("테스트 결과");
        assertThat(content.get(0).qrCodeImageUrl()).contains("cdn.qrust.com");
    }
}
