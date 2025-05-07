package com.qrust.qrcode.domain.entity.vo;

public enum QrCodeType {

    URL("웹사이트"),
    VIDEO("비디오"),
    IMAGE("이미지");

    private final String description;

    QrCodeType(String description) {
        this.description = description;
    }
}
