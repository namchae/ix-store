package com.kakaoix.store.order;

import com.kakaoix.store.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<List<Order>> findByMemberId(Long memberId);
}
