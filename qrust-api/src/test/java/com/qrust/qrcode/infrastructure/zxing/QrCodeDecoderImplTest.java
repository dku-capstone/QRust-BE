package com.qrust.qrcode.infrastructure.zxing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.utils.AesUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QrCodeDecoderImplTest {

    private static final String AES_KEY = "U0VDUkVUX0tFWV9GT1JfUVJfQ1JZUFRP"; // Base64 인코딩된 AES 키

    private AesUtil aesUtil;
    private ObjectMapper objectMapper;

    private QrCodeGeneratorImpl qrCodeGenerator;
    private QrCodeDecoderImpl qrCodeDecoder;

    @BeforeEach
    void setUp() {
        this.aesUtil = new AesUtil(AES_KEY);
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        this.qrCodeGenerator = new QrCodeGeneratorImpl(objectMapper, aesUtil);
        this.qrCodeDecoder = new QrCodeDecoderImpl(objectMapper, aesUtil);
    }

    @Test
    void generateAndDecodeQrCode_shouldReturnOriginalData() throws Exception {
        //given
        QrCodeData original = new QrCodeData(
                "https://example.com",
                "Example Title"
        );

        byte[] byteQrCode = qrCodeGenerator.generateQrCode(original);

        //when
        QrCodeData decoded = qrCodeDecoder.decodeQrCodeData(byteQrCode);

        //then
        assertNotNull(decoded);
        assertEquals(original.getUrl(), decoded.getUrl());
        assertEquals(original.getTitle(), decoded.getTitle());
    }
}