package com.qrust.qrcode.domain.repository;

import com.qrust.qrcode.domain.entity.QrCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrCodeRepository extends JpaRepository<QrCode, Long> {
    Page<QrCode> findAllByUserId(Long userId, Pageable pageable);
}
