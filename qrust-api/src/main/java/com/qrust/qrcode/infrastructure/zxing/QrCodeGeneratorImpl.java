package com.qrust.qrcode.infrastructure.zxing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.qrust.qrcode.application.QrCodeGenerator;
import com.qrust.qrcode.utils.AesUtil;
import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrCodeGeneratorImpl implements QrCodeGenerator {

    private final ObjectMapper objectMapper;
    private final AesUtil aesUtil;

    @Override
    public byte[] generateQrCode(QrCodeData qrCodeData) {

        try {
            // QR 코드 데이터 객체 -> JSON 직렬화 (e.g. {title: "제목", "url" : "https://example.com"})
            String jsonQrCodeData = objectMapper.writeValueAsString(qrCodeData);

            // AES 암호화
            String encryptedJsonQrCodeData = aesUtil.encrypt(jsonQrCodeData);

            //QR 코드 데이터를 QR 코드로 변환 (300 x 300 크기)
            BitMatrix qrMatrix = new QRCodeWriter().encode(encryptedJsonQrCodeData, BarcodeFormat.QR_CODE, 300, 300);

            // QR 코드를 이미지로 변환
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(qrMatrix);

            // 이미지 데이터를 메모리 상에서 처리할 수 있도록 byte stream을 생성
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 이후 BufferedImage 를 위 스트림에 PNG 형식으로 생성
            ImageIO.write(qrImage, "png", out);

            // 메모리에 저장된 PNG 데이터를 바이트 배열로 반환
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("QR 생성 실패", e);
        }
    }
}
