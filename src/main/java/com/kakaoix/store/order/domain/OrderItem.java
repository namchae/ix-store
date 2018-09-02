package com.kakaoix.store.order.domain;

import com.kakaoix.store.order.domain.OrderLine;
import com.kakaoix.store.product.domain.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

// 주문 상품
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @NotEmpty
    @ApiModelProperty(notes = "상품 ID", required = true, example = "1")
    private Long productId;

    @NotEmpty
    @ApiModelProperty(notes = "주문수량", required = true, example = "3")
    private int orderQuantity;

    @Builder
    public OrderItem(Long productId, int orderQuantity) {
        this.productId = productId;
        this.orderQuantity = orderQuantity;
    }

    @Builder
    public OrderLine toEntity(Product product) {
        return OrderLine.builder()
                .product(product)
                .quantity(this.orderQuantity)
                .build();
    }

}
