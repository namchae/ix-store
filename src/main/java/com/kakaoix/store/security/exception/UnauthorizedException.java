package com.kakaoix.store.security.exception;

// 인증실패 Exception 정의
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("token is expired");
    }
}
