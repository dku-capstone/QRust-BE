package com.qrust.qrcode.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.qrcode.infrastructure.zxing.QrCodeDecoderImpl;
import com.qrust.qrcode.infrastructure.zxing.QrCodeGeneratorImpl;
import com.qrust.utils.AesEncryptorUtil;
import org.junit.jupiter.api.BeforeEach;

public abstract class QrCodeTestTemplate {

    protected static final String AES_SECRET_KEY = "U0VDUkVUX0tFWV9GT1JfUVJfQ1JZUFRP";

    protected ObjectMapper objectMapper;
    protected AesEncryptorUtil aesEncryptorUtil;

    protected QrCodeGeneratorImpl qrCodeGenerator;
    protected QrCodeDecoderImpl qrCodeDecoder;

    @BeforeEach
    void setUpCommon() {
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        this.aesEncryptorUtil = new AesEncryptorUtil(AES_SECRET_KEY);
        this.qrCodeGenerator = new QrCodeGeneratorImpl(objectMapper, aesEncryptorUtil);
        this.qrCodeDecoder = new QrCodeDecoderImpl(objectMapper, aesEncryptorUtil);
    }

    protected QrCodeData createTestQrCodeData() {
        return new QrCodeData("https://example.com", "Example Title");
    }

}
