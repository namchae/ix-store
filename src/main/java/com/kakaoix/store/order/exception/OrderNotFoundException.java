package com.kakaoix.store.order.exception;

import lombok.Getter;

// 주문내용을 못찾았을 경우에 대한 Exception 정의
@Getter
public class OrderNotFoundException extends RuntimeException {

    private long orderId;

    public OrderNotFoundException(long orderId) {
        super(orderId + "is not found");
        this.orderId = orderId;
    }
}
