package com.kakaoix.store.member.exception;

// 비밀번호 불일치에 대한 Exception
public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("wrong password");
    }

}
