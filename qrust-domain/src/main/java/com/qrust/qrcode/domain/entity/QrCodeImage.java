package com.qrust.qrcode.domain.entity;

import com.qrust.infrastructure.jpa.shared.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qr_code_image")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class QrCodeImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "qr_code_id", nullable = false)
    private QrCode qrCode;

    private String imageUrl;

}
