package com.qrust.qrcode.infrastructure.jpa;

import com.qrust.qrcode.domain.entity.QrCodeImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrCodeImageRepository extends JpaRepository<QrCodeImage, Long> {

}
