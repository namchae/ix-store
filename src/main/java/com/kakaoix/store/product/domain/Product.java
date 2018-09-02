package com.kakaoix.store.product.domain;

import com.kakaoix.store.product.exception.NotEnoughStackOverException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Long id;

    @Column(name = "prod_name", nullable = false)
    private String productName;

    @Column(name = "prod_image", nullable = false)
    private String productImage;

    @Column(name = "prod_price", nullable = false)
    private Integer productPrice;

    @Column(name = "prod_stock")
    private Integer productStock;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @Builder
    public Product(String productName, String productImage, Integer productPrice, Integer productStock) {
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productStock = productStock;
    }

    // 상품 재고 차감.
    public void removeStock(int orderQuantity) {
        int remainStock = this.productStock - orderQuantity;
        if (remainStock < 0) {
            throw new NotEnoughStackOverException(this.id);
        }
        this.productStock = remainStock;
    }
}
