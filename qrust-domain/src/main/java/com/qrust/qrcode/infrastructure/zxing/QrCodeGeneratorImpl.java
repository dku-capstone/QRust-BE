package com.qrust.qrcode.infrastructure.zxing;

import static com.qrust.exception.qrcode.ErrorMessages.FAIL_CREATE_QR;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.qrust.exception.CustomException;
import com.qrust.exception.error.ErrorCode;
import com.qrust.qrcode.domain.service.QrCodeGenerator;
import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.utils.QrCodeEncryptorUtil;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrCodeGeneratorImpl implements QrCodeGenerator {

    private final ObjectMapper objectMapper;
    private final QrCodeEncryptorUtil qrCodeEncryptorUtil;

    public static final int QR_WIDTH = 300;
    public static final int QR_HEIGHT = 300;

    @Override
    public byte[] generateQrCode(QrCodeData qrCodeData) {

        try {

            //TODO
            // Intent Schema 적용


            // QR 코드 데이터 객체 -> JSON 직렬화 (e.g. {title: "제목", "url" : "https://example.com"})
            String jsonQrCodeData = objectMapper.writeValueAsString(qrCodeData);

            // AES 암호화
            String encryptedJsonQrCodeData = qrCodeEncryptorUtil.encrypt(jsonQrCodeData);

            //QR 코드 데이터를 QR 코드로 변환 (300 x 300 크기)
            BitMatrix qrMatrix = new QRCodeWriter().encode(encryptedJsonQrCodeData, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);

            // QR 코드를 이미지로 변환
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(qrMatrix);

            // 이미지 데이터를 메모리 상에서 처리할 수 있도록 byte stream을 생성
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 이후 BufferedImage 를 위 스트림에 PNG 형식으로 생성
            ImageIO.write(qrImage, "png", out);

            // 메모리에 저장된 PNG 데이터를 바이트 배열로 반환
            return out.toByteArray();

        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, FAIL_CREATE_QR);
        }
    }
}
