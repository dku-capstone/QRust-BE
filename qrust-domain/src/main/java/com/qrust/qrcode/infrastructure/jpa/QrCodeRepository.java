package com.qrust.qrcode.infrastructure.jpa;

import com.qrust.qrcode.domain.entity.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrCodeRepository extends JpaRepository<QrCode, Long> {

}
