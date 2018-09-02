package com.kakaoix.store.interceptor;

import com.kakaoix.store.security.exception.UnauthorizedException;
import com.kakaoix.store.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.google.common.base.Strings.isNullOrEmpty;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

// Jwt Token 생성 및 검증 확인
@Component
public class JwtInterceptor implements HandlerInterceptor {

    public static final String HEADER_TOKEN_KEY = "token";

    private final JwtGenerator jwtGenerator;

    @Autowired
    public JwtInterceptor(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(HEADER_TOKEN_KEY);

        if (!isNullOrEmpty(token)) {
            jwtGenerator.validate(token);
            return true;
        } else {
            throw new UnauthorizedException();
        }
    }
}
