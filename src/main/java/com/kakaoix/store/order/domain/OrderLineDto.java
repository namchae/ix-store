package com.kakaoix.store.order.domain;

import com.kakaoix.store.order.domain.OrderLine;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class OrderLineDto {

    // 주문리스트 응답 DTO
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    @ApiModel(value = "OrderLineDto.Response", description = "주문리스트 응답")
    public static class Response {

        @ApiModelProperty(notes = "상품 이름", required = true, example = "리틀프랜즈 아스크림1")
        private String productName;

        @ApiModelProperty(notes = "상품 가격", required = true, example = "1000")
        private int productPrice;

        @ApiModelProperty(notes = "주문수량", required = true, example = "3")
        private int quantity;

        @Builder
        public Response(OrderLine orderLine) {
            this.productName = orderLine.getProductName();
            this.productPrice = orderLine.getProductPrice();
            this.quantity = orderLine.getQuantity();
        }
    }
}
