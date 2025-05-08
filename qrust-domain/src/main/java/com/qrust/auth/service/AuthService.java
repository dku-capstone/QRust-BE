package com.qrust.auth.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final String AT_PREFIX = "access_token";
    private static final String RT_PREFIX = "refresh_token";

    public void login(String accessToken, String refreshToken, HttpServletResponse response) {
        // TODO: LAX -> Strict
        response.addHeader(
                "Set-Cookie",
                AT_PREFIX + "=" + accessToken + "; Path=/; HttpOnly; SameSite=Lax; Max-Age=1800"
        );
        response.addHeader(
                "Set-Cookie",
                RT_PREFIX + "=" + refreshToken + "; Path=/; HttpOnly; SameSite=Lax; Max-Age=604800"
        );
    }

    public void logout(HttpServletResponse response) {
        Cookie expiredAccess = new Cookie(AT_PREFIX, "");
        expiredAccess.setPath("/");
        expiredAccess.setHttpOnly(true);
        expiredAccess.setMaxAge(0);

        Cookie expiredRefresh = new Cookie(RT_PREFIX, "");
        expiredRefresh.setPath("/");
        expiredRefresh.setHttpOnly(true);
        expiredRefresh.setMaxAge(0);

        response.addCookie(expiredAccess);
        response.addCookie(expiredRefresh);
    }
}
