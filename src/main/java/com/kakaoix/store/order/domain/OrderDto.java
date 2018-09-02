package com.kakaoix.store.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// 주문관련 DTO
public class OrderDto {

    // 주문요청 DTO
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class Execute {

        @NotEmpty
        @ApiModelProperty(notes = "구매자", required = true, example = "남채희")
        private String buyer;

        @Valid
        @ApiModelProperty(notes = "주소", required = true)
        private Address address;

        @NotEmpty
        @ApiModelProperty(notes = "요청 상품 리스트", required = true)
        private List<OrderItem> orderItems;

        @Builder
        public Execute(String buyer, Address address, List<OrderItem> orderItems) {
            this.buyer = buyer;
            this.address = address;
            this.orderItems = orderItems;
        }

        public Order toEntity(Long memberId, List<OrderLine> orderLines) {
            return Order.builder()
                    .memberId(memberId)
                    .execute(this)
                    .orderLines(orderLines)
                    .build();
        }
    }

    // 주문정보 응답 DTO
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class Response {

        @ApiModelProperty(notes = "주문ID", required = true, example = "1")
        private Long id;

        @ApiModelProperty(notes = "회원ID", required = true, example = "1")
        private Long memberId;

        @ApiModelProperty(notes = "주문가격", required = true, example = "100000")
        private Integer totalPrice;

        @ApiModelProperty(notes = "주문상태", required = true, example = "주문완료")
        private String orderStatus;

        @ApiModelProperty(notes = "주문날짜", required = true, example = "2018-09-01 19:00:00")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime orderDate;

        @ApiModelProperty(notes = "취소날짜", required = true, example = "2018-09-02 02:00:00")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime cancelDate;

        @ApiModelProperty(notes = "주문자", required = true, example = "남채희")
        private String buyer;

        private Address address;

        @ApiModelProperty(notes = "주문리스트", required = true, example = "")
        private List<OrderLineDto.Response> orderLineResponses;

        public Response(Order order) {
            this.id = order.getId();
            this.memberId = order.getMemberId();
            this.totalPrice = order.getTotalPrice();
            this.orderStatus = order.getOrderStatus().getDescription();
            this.orderDate = order.getOrderDate();
            this.cancelDate = order.getCancelDate();
            this.buyer = order.getBuyer();
            this.address = order.getAddress();
            this.orderLineResponses = order.getOrderLines().stream()
                    .map(OrderLineDto.Response::new)
                    .collect(Collectors.toList());
        }

    }
}
