package com.jpabook.jpashop.api;

import com.jpabook.jpashop.dto.Result;
import com.jpabook.jpashop.dto.request.order.CreateOrderRequest;
import com.jpabook.jpashop.dto.request.order.ReadOrderRequest;
import com.jpabook.jpashop.dto.response.order.OrderResponse;
import com.jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/order")
    public Result<List<OrderResponse>> findOrder(@RequestParam Long memberId
            , @RequestParam(required = false) Long orderId
            , @RequestParam(required = false) String itemName
            , @RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "20") int size) {
        ReadOrderRequest request = ReadOrderRequest.builder()
                .memberId(memberId)
                .orderId(orderId)
                .itemName(itemName)
                .build();
        PageRequest pageRequest = PageRequest.of(page, size);

        return orderService.findOrders(request, pageRequest);
    }

    @GetMapping("/all")
    public Result<List<OrderResponse>> findAllOrder() {
        return orderService.findAll();
    }
}
