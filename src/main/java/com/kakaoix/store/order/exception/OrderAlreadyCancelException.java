package com.kakaoix.store.order.exception;

import lombok.Getter;

// 중복 취소요청에 대한 Exception 정의.
@Getter
public class OrderAlreadyCancelException extends RuntimeException{
    private final long orderId;

    public OrderAlreadyCancelException(long orderId) {
        super("already cancel : " + orderId);
        this.orderId = orderId;
    }
}
