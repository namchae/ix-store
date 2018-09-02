package com.kakaoix.store.order.domain;

import com.kakaoix.store.order.code.OrderStatus;
import com.kakaoix.store.order.exception.OrderAlreadyCancelException;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Entity
@Table(name = "orders")
@Getter
@ToString
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false) // 총 결제금액
    private Long memberId;

    @Column(name = "order_status", nullable = false) // 주문상태
    private OrderStatus orderStatus;

    @Column(name = "order_date", nullable = false) // 주문날짜
    private LocalDateTime orderDate;

    @Column(name = "cancel_date") // 주문날짜
    private LocalDateTime cancelDate;
    @NotEmpty
    @Column(name = "buyer", nullable = false) // 주문자
    private String buyer;

    @Embedded
    private Address address; // 주소

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch  = FetchType.LAZY)
    private List<OrderLine> orderLines;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @Builder
    public Order(Long memberId, OrderDto.Execute execute, List<OrderLine> orderLines) {
        this.memberId = memberId;
        this.orderStatus = OrderStatus.ORDER;
        this.orderDate = LocalDateTime.now();
        this.buyer = execute.getBuyer();
        this.address = execute.getAddress();
        orderLines.forEach(this::addOrderLine);
    }
    // 주문취소
    public Order cancel() {
        if (this.getOrderStatus() == OrderStatus.CANCEL) {
            throw new OrderAlreadyCancelException(id);
        }
        this.orderStatus = OrderStatus.CANCEL;
        this.cancelDate = LocalDateTime.now();
        return this;
    }

    // 주문 가격.
    public int getTotalPrice() {
        return orderLines.stream()
                .mapToInt(OrderLine::getPrice)
                .sum();
    }

    // 주문 리스트 처리.
    private void addOrderLine(OrderLine orderLine) {
        if (isNull(orderLines)) {
            orderLines = new ArrayList<>();
        }
        orderLines.add(orderLine);
        orderLine.setOrder(this);
    }
}
