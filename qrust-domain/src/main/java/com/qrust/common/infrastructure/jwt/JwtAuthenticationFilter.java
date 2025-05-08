package com.qrust.common.infrastructure.jwt;

import static com.qrust.exception.auth.ErrorMessages.INVALID_TOKEN;

import com.qrust.auth.infrastructure.TokenService;
import com.qrust.auth.service.AuthService;
import com.qrust.common.config.JwtConfig;
import com.qrust.common.exception.CustomException;
import com.qrust.common.exception.error.ErrorCode;
import com.qrust.user.domain.entity.User;
import com.qrust.user.domain.entity.vo.UserRole;
import com.qrust.user.domain.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final UserService userService;
    private final TokenService tokenService;
    private final AuthService authService;

    private static final int AT_COOKIE_MAX_AGE = (int) (JwtConfig.ACCESS_TOKEN_EXPIRATION_TIME / 1000);
    private static final int RT_COOKIE_MAX_AGE = (int) (JwtConfig.REFRESH_TOKEN_EXPIRATION_TIME / 1000);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String accessToken = getCookieValue(request, "access_token");
        String refreshToken = getCookieValue(request, "refresh_token");

        if (accessToken != null && jwtValidator.validateToken(accessToken)) {
            applyAuthentication(accessToken);
        } else if (refreshToken != null && jwtValidator.validateToken(refreshToken)) {
            String userIdStr = jwtValidator.getSubject(refreshToken);
            Long userId = Long.parseLong(userIdStr);
            User user = userService.getById(userId);
            String rtKey = tokenService.getRTKey(userId);

            if (!tokenService.isValid(rtKey, refreshToken)) {
                authService.logout(response);
                throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, INVALID_TOKEN);
            }
            UserRole userRole = user.getUserRole();
            String newAccessToken = jwtProvider.generateAccessToken(userId, userRole);
            String newRefreshToken = jwtProvider.generateRefreshToken(userId, userRole); // RTR

            tokenService.saveRT(rtKey, newRefreshToken);

            addCookie(response, "access_token", newAccessToken, AT_COOKIE_MAX_AGE);
            addCookie(response, "refresh_token", newRefreshToken, RT_COOKIE_MAX_AGE);

            applyAuthentication(newAccessToken);
        }

        filterChain.doFilter(request, response);
    }

    private void applyAuthentication(String token) {
        Long userId = Long.parseLong(jwtValidator.getSubject(token));
        String role = jwtValidator.getRole(token).name();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userId,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAgeSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // TODO 개발용 (HTTP에서도 쿠키 설정 가능)
        cookie.setMaxAge(maxAgeSeconds);
        response.addCookie(cookie);
    }

    private String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
