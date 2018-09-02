package com.kakaoix.store.product.exception;

import lombok.Getter;

// 재고 부족 Exception
@Getter
public class NotEnoughStackOverException extends RuntimeException {

    private final long productId;

    public NotEnoughStackOverException(long productId) {
        super(productId + "Not enough Stack");
        this.productId = productId;
    }
}
