package com.kakaoix.store.order.domain;

import com.kakaoix.store.product.domain.Product;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "order_line")
@Getter
@ToString
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_line_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @Setter
    private Order order;

    @Column(name = "prod_name", nullable = false) // 상품이름
    private String productName;

    @Column(name = "prod_price", nullable = false) // 상품가격
    private Integer productPrice;

    @Column(name = "quantity", nullable = false) // 주문 개수
    private Integer quantity;

    @Builder
    public OrderLine(Product product, int quantity) {
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.quantity = quantity;
        product.removeStock(quantity);
    }

    public int getPrice() {
        return this.productPrice * this.quantity;
    }
}
