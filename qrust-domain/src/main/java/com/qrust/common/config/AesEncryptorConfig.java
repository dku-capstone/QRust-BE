package com.qrust.common.config;

import static com.qrust.exception.qrcode.ErrorMessages.INVALID_SECRET_KEY;

import com.qrust.exception.CustomException;
import com.qrust.exception.error.ErrorCode;
import com.qrust.utils.AesEncryptorUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class AesEncryptorConfig {

    @Value("${crypto.aes.key:}")
    private String base64Key;

    @Bean
    public AesEncryptorUtil aesEncryptor() {
        if (!StringUtils.hasText(base64Key)) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, INVALID_SECRET_KEY);
        }
        return new AesEncryptorUtil(base64Key);
    }
}
