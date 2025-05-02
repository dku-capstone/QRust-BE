package com.qrust.config;

import com.qrust.qrcode.utils.AesUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class AesEncryptorConfig {

    @Bean
    public AesUtil aesEncryptor(@Value("${crypto.aes.key:}") String base64Key) {
        if (!StringUtils.hasText(base64Key)) {
            throw new IllegalStateException("필수 환경 변수 'CRYPTO_AES_KEY'가 설정되지 않았거나 비어 있습니다.");
        }
        return new AesUtil(base64Key);
    }
}
