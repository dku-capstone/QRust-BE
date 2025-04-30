package com.qrust.user.domain.repository;

import com.qrust.user.domain.entity.Password;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Long> {
    Optional<Password> findByUserId(Long userId);
}
