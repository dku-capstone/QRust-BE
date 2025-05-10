package com.qrust.auth.resolver;

import static com.qrust.exception.auth.ErrorMessages.EMPTY_USER;

import com.qrust.annotation.user.LoginUser;
import com.qrust.common.exception.CustomException;
import com.qrust.common.exception.error.ErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class)
                && (Long.class.equals(parameter.getParameterType()) || long.class.equals(parameter.getParameterType()));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Long) {
            return principal;
        }

        if (Long.class.equals(parameter.getParameterType())) {
            return null;
        }

        throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, EMPTY_USER);
    }
}
