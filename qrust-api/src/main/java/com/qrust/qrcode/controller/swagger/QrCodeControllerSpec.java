package com.qrust.qrcode.controller.swagger;

import com.qrust.common.dto.ApiResponse;
import com.qrust.qrcode.dto.QrCodeGenerateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "QR 코드 생성 API")
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
    ApiResponse<?> generateQrCode(@RequestBody QrCodeGenerateRequestDto dto, Long userId);

    String CREATE_QR_CODE_REQUEST = """
            {
              "url": "https://example.com",
              "title": "QR 코드 제목"
            }
            """;
}
