package com.qrust.qrcode.infrastructure.zxing;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.utils.AesUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QrCodeGeneratorImplTest {

    private static final String AES_SECRET_KEY = "U0VDUkVUX0tFWV9GT1JfUVJfQ1JZUFRP"; // Base64 AES 키

    private ObjectMapper objectMapper;
    private AesUtil aesUtil;

    private QrCodeGeneratorImpl qrCodeGenerator;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        aesUtil = new AesUtil(AES_SECRET_KEY);
        qrCodeGenerator = new QrCodeGeneratorImpl(objectMapper, aesUtil);
    }

    @Test
    void generateQrCode() throws IOException {
        //given
        QrCodeData qrCodeData = new QrCodeData(
                "https://example.com",
                "Example Title"
        );

        //when
        byte[] qrCodeImage = qrCodeGenerator.generateQrCode(qrCodeData);

        Path outputPath = Paths.get("src/test/resources/generated_qr.png");
        Files.write(outputPath, qrCodeImage);

        //then
        assertNotNull(qrCodeImage);
        assertTrue(qrCodeImage.length > 0);
    }
}
