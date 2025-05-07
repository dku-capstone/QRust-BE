package com.qrust.user.domain.service;

import static com.qrust.exception.user.ErrorMessages.PASSWORD_NOT_FOUND;

import com.qrust.common.exception.CustomException;
import com.qrust.common.exception.error.ErrorCode;
import com.qrust.user.domain.entity.Password;
import com.qrust.user.domain.repository.PasswordRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PasswordService {
    private final PasswordRepository passwordRepository;

    @Transactional(readOnly = true)
    public Password getByUserId(Long userId) {
        return findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE,PASSWORD_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Optional<Password> findByUserId(Long userId) {
        return passwordRepository.findByUserId(userId);
    }

    @Transactional
    public Password save(Password password) {
        return passwordRepository.save(password);
    }
}
