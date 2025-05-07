package com.qrust.qrcode.domain.entity;

import com.qrust.common.infrastructure.jpa.shared.BaseEntity;
import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.qrcode.domain.entity.vo.QrCodeStatus;
import com.qrust.qrcode.domain.entity.vo.QrCodeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
//TODO
// 인덱스 추가
@Entity
@Table(name = "qr_code")
@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QrCode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Embedded
    private QrCodeData qrCodeData;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "qr_code_image_id", referencedColumnName = "id", nullable = false)
    private QrCodeImage qrCodeImage;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "qr_code_type", nullable = false)
    private QrCodeType qrCodeType = QrCodeType.URL;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "qr_code_status", nullable = false)
    private QrCodeStatus qrCodeStatus = QrCodeStatus.ACTIVE;

    public void updateQrCode(String title, QrCodeStatus status) {
        if(title != null){
            this.qrCodeData = new QrCodeData(this.qrCodeData.getUrl(), title);
        }

        if(status != null){
            changeStatus(status);
        }
    }

    private void changeStatus(QrCodeStatus qrCodeStatus) {
        this.qrCodeStatus = qrCodeStatus;
    }
}
