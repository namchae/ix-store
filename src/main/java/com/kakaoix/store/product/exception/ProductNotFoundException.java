package com.kakaoix.store.product.exception;

import lombok.Getter;

// 상품정보 못찾았을 경우 Exception 정의.
@Getter
public class ProductNotFoundException extends RuntimeException {

    private final long productId;

    public ProductNotFoundException(long productId) {
        super(productId + "is not found");
        this.productId = productId;
    }
}
