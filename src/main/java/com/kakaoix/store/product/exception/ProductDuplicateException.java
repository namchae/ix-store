package com.kakaoix.store.product.exception;

import lombok.Getter;

// 상품 이름중복 등록에 대한 Exception 정의.
@Getter
public class ProductDuplicateException extends RuntimeException {
    private final String productName;

    public ProductDuplicateException(String productName) {
        super(productName + " is existed");
        this.productName = productName;
    }
}
