package com.qrust.utils;

public abstract class QrCodeEncryptorUtil {

    public abstract String encrypt(String plaintext);
    public abstract String decrypt(String encryptedBase64);
}
