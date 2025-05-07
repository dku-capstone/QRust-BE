package com.qrust.qrcode.controller.swagger;

import com.qrust.common.dto.ApiResponse;
import com.qrust.common.dto.PageResponse;
import com.qrust.qrcode.dto.QrCodeGenerateRequestDto;
import com.qrust.qrcode.dto.QrCodeListResponseDto;
import com.qrust.qrcode.dto.QrCodeResponseDto;
import com.qrust.qrcode.dto.QrCodeSearchRequestDto;
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
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "QR 코드 생성 요청", value = CREATE_QR_CODE_REQUEST)
                                    }
                            )
                    }
            )
    )
    ApiResponse<?> generateQrCode(@RequestBody QrCodeGenerateRequestDto dto,
            @Parameter(description = "사용자 ID") Long userId);


    @Operation(summary = "QR 코드 목록 조회 (사용자별)")
    ApiResponse<PageResponse<QrCodeListResponseDto>> getAllQrCodes(
            @Parameter(description = "사용자 ID") Long userId,
            @Parameter(description = "페이지 번호", example = "0") int page,
            @Parameter(description = "페이지 크기", example = "10") int size);


    @Operation(summary = "QR 코드 단건 조회")
    ApiResponse<QrCodeResponseDto> getQrCode(
            @Parameter(description = "QR 코드 ID") Long qrCodeId);



    @Operation(summary = "QR 코드 조건 검색",
            requestBody = @RequestBody(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "조건 검색 예시", value = SEARCH_QR_CODE_REQUEST)
                                    }
                            )
                    }
            )
    )
    ApiResponse<PageResponse<QrCodeListResponseDto>> searchQrCode(
            @RequestBody QrCodeSearchRequestDto dto,
            @Parameter(description = "페이지 번호", example = "0") int page,
            @Parameter(description = "페이지 크기", example = "10") int size);


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
              "start": "2024-01-01T00:00:00",
              "end": "2025-12-31T23:59:59"
            }
            """;
}
