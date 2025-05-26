package com.qrust.qrcode.controller.swagger;

import com.qrust.annotation.user.LoginUser;
import com.qrust.dto.ApiResponse;
import com.qrust.dto.PageResponse;
import com.qrust.qrcode.dto.request.QrCodeGenerateRequestDto;
import com.qrust.qrcode.dto.request.QrCodeSearchRequestDto;
import com.qrust.qrcode.dto.request.QrCodeUpdateRequestDto;
import com.qrust.qrcode.dto.response.QrCodeListResponseDto;
import com.qrust.qrcode.dto.response.QrCodeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "QR 코드 API")
public interface QrCodeControllerSpec {

    @Operation(summary = "QR 코드 생성",
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "QR 코드 생성 요청", value = CREATE_QR_CODE_REQUEST)
                    )
            )
    )
    ApiResponse<?> generateQrCode(@RequestBody QrCodeGenerateRequestDto dto,
            @Parameter(description = "사용자 ID") Long userId);

    @Operation(summary = "QR 코드 목록 조회 (사용자별)")
    ApiResponse<PageResponse<QrCodeListResponseDto>> getAllQrCodes(
            @Parameter(hidden=true) @LoginUser Long userId,
            @Parameter(description = "페이지 번호", example = "0") int page,
            @Parameter(description = "페이지 크기", example = "10") int size);

    @Operation(summary = "QR 코드 단건 조회")
    ApiResponse<QrCodeResponseDto> getQrCode(
            @Parameter(description = "QR 코드 ID") Long qrCodeId);

    @Operation(summary = "QR 코드 조건 검색",
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "조건 검색 예시", value = SEARCH_QR_CODE_REQUEST)
                    )
            )
    )
    ApiResponse<PageResponse<QrCodeListResponseDto>> searchQrCode(
            @RequestBody QrCodeSearchRequestDto dto,
            @Parameter(description = "페이지 번호", example = "0") int page,
            @Parameter(description = "페이지 크기", example = "10") int size);

    @Operation(summary = "QR 코드 수정",
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "QR 코드 수정 요청", value = UPDATE_QR_CODE_REQUEST)
                    )
            )
    )
    ApiResponse<?> updateQrCode(
            @Parameter(description = "QR 코드 ID") Long qrCodeId,
            @RequestBody QrCodeUpdateRequestDto dto);

    @Operation(summary = "QR 코드 삭제 (상태 만료 변경 후 삭제)")
    ApiResponse<?> deleteQrCode(
            @Parameter(description = "QR 코드 ID") Long qrCodeId);

    String CREATE_QR_CODE_REQUEST = """
            {
              "url": "https://example.com",
              "title": "QR 코드 제목"
            }
            """;

    String SEARCH_QR_CODE_REQUEST = """
            {
              "title": "검색할 제목",
              "type": "URL",
              "status": "ACTIVE",
              "start": "2024-01-01",
              "end": "2025-12-31"
            }
            """;

    String UPDATE_QR_CODE_REQUEST = """
            {
              "title": "수정할 제목",
              "status": "EXPIRED"
            }
            """;
}
