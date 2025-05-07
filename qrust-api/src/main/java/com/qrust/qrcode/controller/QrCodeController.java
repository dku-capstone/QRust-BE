package com.qrust.qrcode.controller;

import com.qrust.common.dto.ApiResponse;
import com.qrust.common.dto.PageResponse;
import com.qrust.qrcode.application.QrCodeCommandService;
import com.qrust.qrcode.application.QrCodeGeneratorService;
import com.qrust.qrcode.application.QrCodeQueryService;
import com.qrust.qrcode.controller.swagger.QrCodeControllerSpec;
import com.qrust.qrcode.dto.request.QrCodeGenerateRequestDto;
import com.qrust.qrcode.dto.request.QrCodeSearchRequestDto;
import com.qrust.qrcode.dto.request.QrCodeUpdateRequestDto;
import com.qrust.qrcode.dto.response.QrCodeListResponseDto;
import com.qrust.qrcode.dto.response.QrCodeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/qrcode")
public class QrCodeController implements QrCodeControllerSpec {

    private final QrCodeGeneratorService qrCodeGeneratorService;
    private final QrCodeQueryService qrCodeQueryService;
    private final QrCodeCommandService qrCodeCommandService;

    //TODO
    // Authenticated User 연동 + UserDetail -> userId 전달

    @PostMapping("/generate")
    public ApiResponse<?> generateQrCode(@RequestBody QrCodeGenerateRequestDto dto, Long userId) {
        String qrCodeImageUrl = qrCodeGeneratorService.generateQrCode(dto.toQrCodeData(), userId);

        return ApiResponse.created(qrCodeImageUrl);
    }

    //TODO
    // QR 코드의 Status 필드가 expired 인 경우 분기 처리

    // 목록 조회(페이징)
    @GetMapping("/user/{userId}")
    public ApiResponse<PageResponse<QrCodeListResponseDto>> getAllQrCodes(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<QrCodeListResponseDto> qrCodeList = qrCodeQueryService.getQrCodes(userId, PageRequest.of(page, size));

        return ApiResponse.ok(qrCodeList);
    }

    // 단건 조회
    @GetMapping("/{qrCodeId}")
    public ApiResponse<QrCodeResponseDto> getQrCode(@PathVariable(name = "qrCodeId") Long qrCodeId) {
        QrCodeResponseDto qrCode = qrCodeQueryService.getQrCode(qrCodeId);

        return ApiResponse.ok(qrCode);
    }

    //조건 조회
    @PostMapping("/search")
    public ApiResponse<PageResponse<QrCodeListResponseDto>> searchQrCode(
            @RequestBody QrCodeSearchRequestDto dto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<QrCodeListResponseDto> result = qrCodeQueryService.searchQrCode(
                dto, PageRequest.of(page, size));

        return ApiResponse.ok(result);
    }

    // 수정
    @PatchMapping("/{qrCodeId}")
    public ApiResponse<?> updateQrCode(@PathVariable(name = "qrCodeId") Long qrCodeId,
                                        @RequestBody QrCodeUpdateRequestDto dto) {

        qrCodeCommandService.updateQrCode(dto, qrCodeId);

        return ApiResponse.ok(null);
    }

    // 삭제
    @DeleteMapping("/{qrCodeId}")
    public ApiResponse<?> deleteQrCode(@PathVariable(name = "qrCodeId") Long qrCodeId) {
        qrCodeCommandService.deleteQrCode(qrCodeId);

        return ApiResponse.ok(null);
    }
}
