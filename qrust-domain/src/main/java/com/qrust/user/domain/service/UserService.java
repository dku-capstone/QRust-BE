package com.qrust.user.domain.service;

import static com.qrust.exception.user.ErrorMessages.USER_NOT_FOUND;

import com.qrust.common.exception.CustomException;
import com.qrust.common.exception.error.ErrorCode;
import com.qrust.user.domain.entity.User;
import com.qrust.user.domain.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public User getByEmail(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE, USER_NOT_FOUND));
    }

    @Transactional
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE, USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
