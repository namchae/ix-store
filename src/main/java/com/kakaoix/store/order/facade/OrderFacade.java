package com.kakaoix.store.order.facade;

import com.kakaoix.store.member.MemberService;
import com.kakaoix.store.order.OrderRepository;
import com.kakaoix.store.order.domain.Order;
import com.kakaoix.store.order.domain.OrderDto;
import com.kakaoix.store.order.domain.OrderItem;
import com.kakaoix.store.order.domain.OrderLine;
import com.kakaoix.store.product.ProductService;
import com.kakaoix.store.product.domain.Product;
import com.kakaoix.store.security.TokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderFacade {

    private final MemberService memberService;

    private final ProductService productService;

    private final OrderRepository orderRepository;

    @Autowired
    public OrderFacade(MemberService memberService,
                       ProductService productService,
                       OrderRepository orderRepository) {
        this.memberService = memberService;
        this.productService = productService;
        this.orderRepository = orderRepository;
    }

    // 상품 주문.
    public void execute(String token, OrderDto.Execute execute) {
        List<OrderLine> orderLines = new ArrayList<>();

        List<OrderItem> orderItems = execute.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            Product product = productService.getProductById(orderItem.getProductId());
            orderLines.add(orderItem.toEntity(product));
        }

        Long memberId = memberService.getTokenByMemberId(token);
        orderRepository.save(execute.toEntity(memberId, orderLines));
    }

    public List<OrderDto.Response> findByMember(String token) {
        Long memberId = memberService.getTokenByMemberId(token);

        Optional<List<Order>> ordersOptional = orderRepository.findByMemberId(memberId);
        return ordersOptional.get().stream()
                .map(OrderDto.Response::new)
                .collect(Collectors.toList());
    }
}
