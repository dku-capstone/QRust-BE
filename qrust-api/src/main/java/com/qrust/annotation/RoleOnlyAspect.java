package com.qrust.annotation;

import static com.qrust.exception.auth.ErrorMessages.INVALID_TOKEN;
import static com.qrust.exception.error.ErrorCode.INVALID_INPUT_VALUE;
import static com.qrust.exception.user.ErrorMessages.USER_NOT_AUTHORIZED;

import com.qrust.exception.CustomException;
import com.qrust.user.domain.entity.vo.UserRole;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RoleOnlyAspect {

    @Before("@within(roleOnly) || @annotation(roleOnly)")
    public void checkRole(RoleOnly roleOnly) {
        UserRole actualRole = getCurrentRole();
        boolean hasRequiredRole = Arrays.stream(roleOnly.value())
                .anyMatch(required -> UserRole.isRoleIncluded(actualRole, required));

        if (!hasRequiredRole) {
            throw new CustomException(INVALID_INPUT_VALUE, USER_NOT_AUTHORIZED);
        }
    }

    private UserRole getCurrentRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new CustomException(INVALID_INPUT_VALUE, INVALID_TOKEN);
        }

        String authority = authentication.getAuthorities().stream()
                .findFirst()
                .map(granted -> granted.getAuthority())
                .orElseThrow(() -> new CustomException(INVALID_INPUT_VALUE, USER_NOT_AUTHORIZED));

        return UserRole.fromStringOrThrow(authority.replace("ROLE_", ""));
    }
}
