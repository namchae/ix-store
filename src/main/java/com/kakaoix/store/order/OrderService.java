package com.kakaoix.store.order;


import com.kakaoix.store.order.domain.Order;
import com.kakaoix.store.order.domain.OrderDto;
import com.kakaoix.store.order.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDto.Response findOrderResponseById(Long orderId) {
        Order order = findByOrderId(orderId);
        return new OrderDto.Response(order);
    }

    @Transactional
    public void cancel(Long orderId) {
        Order order = findByOrderId(orderId);
        orderRepository.save(order.cancel());
    }

    private Order findByOrderId(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            throw new OrderNotFoundException(orderId);
        }
        return orderOptional.get();
    }
}
