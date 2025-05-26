package com.qrust.qrcode.infrastructure.zxing;

import static com.qrust.exception.qrcode.ErrorMessages.FAIL_DECRYPT_QR;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qrust.exception.CustomException;
import com.qrust.exception.error.ErrorCode;
import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.qrcode.domain.service.QrCodeDecoder;
import com.qrust.utils.QrCodeEncryptorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrCodeDecoderImpl implements QrCodeDecoder {

    private final ObjectMapper objectMapper;
    private final QrCodeEncryptorUtil qrCodeEncryptorUtil;

    @Override
    public QrCodeData decodeQrCodeData(String encryptedQrCodeData) {

        try {
            String decryptedJson = qrCodeEncryptorUtil.decrypt(encryptedQrCodeData);

            return objectMapper.readValue(decryptedJson, QrCodeData.class);

        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, FAIL_DECRYPT_QR);
        }
    }
}
