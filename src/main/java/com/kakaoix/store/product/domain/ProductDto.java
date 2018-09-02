package com.kakaoix.store.product.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class ProductDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    @ApiModel(value = "ProductDto.Register", description = "상품등록")
    public static class Register {

        @NotBlank
        @ApiModelProperty(notes = "상품 이름", required = true, example = "리틀프랜즈 테스트1")
        private String productName;

        @NotBlank
        @ApiModelProperty(notes = "상품 이미지경로", required = true, example = "https://img1.daumcdn.net/thumb/R200x200/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ffriends%2Fprod%2Fproduct%2FFRPBAPIRK0001_AW_00.jpg")
        private String productImage;

        @ApiModelProperty(notes = "상품 가격", required = true, example = "1000")
        private Integer productPrice;

        @ApiModelProperty(notes = "재고 수량", required = true, example = "10")
        private Integer productStock;

        @Builder
        public Register(String productName, String productImage, Integer productPrice, Integer productStock) {
            this.productName = productName;
            this.productImage = productImage;
            this.productPrice = productPrice;
            this.productStock = productStock;
        }

        public Product toEntity() {
            return Product.builder()
                    .productName(this.productName)
                    .productImage(this.productImage)
                    .productPrice(this.productPrice)
                    .productPrice(this.productStock)
                    .build();
        }
    }

    // TODO: update
    public static class Update {

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ApiModel(value = "ProductDto.Response", description = "상품 응답")
    public static class Response {

        @ApiModelProperty(notes = "상품 ID", required = true, example = "1")
        private Long productId;

        @ApiModelProperty(notes = "상품 이름", required = true, example = "리틀프랜즈 아스크림1")
        private String productName;

        @ApiModelProperty(notes = "상품 이미지경로", required = true, example = "https://img1.daumcdn.net/thumb/R200x200/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ffriends%2Fprod%2Fproduct%2FFRPBAPIRK0001_AW_00.jpg")
        private String productImage;

        @ApiModelProperty(notes = "상품 가격", required = true, example = "1000")
        private Integer productPrice;

        @ApiModelProperty(notes = "상품 재고량", required = true, example = "10")
        private Integer productStock;

        @Builder
        public Response(Product product) {
            this.productId = product.getId();
            this.productName = product.getProductName();
            this.productImage = product.getProductImage();
            this.productPrice = product.getProductPrice();
            this.productStock = product.getProductStock();
        }
    }


}
