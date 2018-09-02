package com.kakaoix.store.order.code;


import lombok.Getter;

// 주문상태에 대한 코드 정의.
public enum OrderStatus {

    ORDER(0, "주문완료"),
    CANCEL(1, "주문취소");

    @Getter
    private int code;

    @Getter
    private String description;

    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
}