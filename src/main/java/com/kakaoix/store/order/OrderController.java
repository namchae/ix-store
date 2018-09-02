package com.kakaoix.store.order;

import com.kakaoix.store.order.domain.OrderDto;
import com.kakaoix.store.order.facade.OrderFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.kakaoix.store.security.JwtGenerator.SAMPLE_TOKEN;

@Slf4j
@Api("주문 API")
@RequestMapping("orders")
@RestController
public class OrderController {

    private final OrderFacade orderFacade;

    private final OrderService orderService;

    @Autowired
    public OrderController( OrderFacade orderFacade,
                            OrderService orderService) {
        this.orderFacade = orderFacade;
        this.orderService = orderService;
    }

    @ApiOperation(value = "주문 요청", notes = "요청된 주문 정보를 등록")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void orderExecute(
            @ApiParam(value = "사용자 요청 토큰", required = true, example = SAMPLE_TOKEN) @RequestHeader("token") String token,
            @ApiParam(value = "주문 요청 정보", required = true) @Valid @RequestBody OrderDto.Execute execute) {
        orderFacade.execute(token, execute);
    }

    @ApiOperation(value = "주문 정보", notes = "요청된 ID 기준의 주문정보 가져오기")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto.Response findById(
            @ApiParam(value = "주문 ID", required = true, example = "1") @PathVariable Long id) {
        return orderService.findOrderResponseById(id);
    }

    @ApiOperation(value = "회원 주문 리스트", notes = "요청된 회원ID 기준의 주문 리스트 가져오기")
    @GetMapping("/member")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto.Response> findByMemberId(
            @ApiParam(value = "사용자 요청 토큰", required = true, example = SAMPLE_TOKEN) @RequestHeader("token") String token) {
        return orderFacade.findByMember(token);
    }

    @ApiOperation(value = "주문 취소", notes = "요청된 ID 기준의 주문취소")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(
            @ApiParam(value = "주문 ID", required = true, example = "1") @PathVariable Long id) {
        orderService.cancel(id);
    }

}

