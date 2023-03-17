package com.jpabook.jpashop.api;

import com.jpabook.jpashop.dto.request.order.CreateOrderRequest;
import com.jpabook.jpashop.dto.response.order.OrderResponse;
import com.jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/order")
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/order")
    public OrderResponse order(CreateOrderRequest request) {
        return orderService.order(request);
    }
}
