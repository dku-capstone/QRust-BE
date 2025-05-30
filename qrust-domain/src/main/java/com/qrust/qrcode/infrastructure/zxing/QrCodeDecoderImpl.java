package com.qrust.qrcode.infrastructure.zxing;

import static com.qrust.exception.qrcode.ErrorMessages.FAIL_DECRYPT_QR;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.qrust.exception.CustomException;
import com.qrust.exception.error.ErrorCode;
import com.qrust.qrcode.domain.service.QrCodeDecoder;
import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.utils.QrCodeEncryptorUtil;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrCodeDecoderImpl implements QrCodeDecoder {

    private final ObjectMapper objectMapper;
    private final QrCodeEncryptorUtil qrCodeEncryptorUtil;

    @Override
    public QrCodeData decodeQrCodeData(byte[] qrCodeImageBytes) {

        try {
            // 1. 바이트 배열 → BufferedImage
            ByteArrayInputStream in = new ByteArrayInputStream(qrCodeImageBytes);
            BufferedImage image = ImageIO.read(in);

            // 2. QR 디코딩 -> 문자열 추출
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(bitmap);

            //3. AES 복호화
            String decryptedJson = qrCodeEncryptorUtil.decrypt(result.getText());

            // 4. 결과 문자열(JSON) → QrCodeData 객체로 역직렬화
            return objectMapper.readValue(decryptedJson, QrCodeData.class);

        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, FAIL_DECRYPT_QR);
        }
    }
}
