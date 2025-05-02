package com.qrust.confing;

import com.qrust.qrcode.utils.AesUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AesEncryptorConfig {

    @Bean
    public AesUtil aesEncryptor(@Value("${crypto.aes.key}") String base64Key) {
        return new AesUtil(base64Key);
    }
}
