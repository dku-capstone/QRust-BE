package com.qrust.qrcode.domain.entity;

import com.qrust.infrastructure.jpa.shared.BaseEntity;
import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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

    @Column(name="is_encrypted", nullable = false)
    private boolean isEncrypted = false;

    @Column(name="is_expired", nullable = false)
    private boolean isExpired = false;
}
