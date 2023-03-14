package com.jpabook.jpashop.api;

import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.dto.response.Result;
import com.jpabook.jpashop.dto.response.order.SimpleOrderDto;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.apache.naming.factory.ResourceLinkFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


/**
 * xToOne (ManyToOne, OneToOne)
 * 이런애들 최적화하는법
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    /**
     * 이 메서드는 StackOverFlow 가 발생한다
     * Domain 객체에서 자기들끼리 서로를 포함하기때문에 무한재귀에 걸리게되어서 StackOverFlow 가 발생한다
     * 더 나아가서 JsonIgnore 를 설정해줘도 다른 문제점이 발생하는데 설명은 생략한다
     * 이런방식으로 Api 를 만들면 안되기 때문에다 ㅎ.
     *
     * @return
     */
    @GetMapping("/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.solveByJpql(new OrderSearch());
        // Lazy 로딩 강제 초기화
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return orders;
    }

    @GetMapping("/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        List<Order> orders = orderRepository.solveByJpql(new OrderSearch());
        return orders.stream().map(SimpleOrderDto::new)
        .collect(Collectors.toList());

    }

    @GetMapping("/v3/simple-orders")
    public Result<List<SimpleOrderDto>> orderV3() {
        List<Order> orders = orderRepository.fetchJoin();
        List<SimpleOrderDto> collect = orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
        return new Result<>(collect);
    }

    @GetMapping("/v4/simple-orders")
    public Result<List<SimpleOrderDto>> orderV4() {
        List<SimpleOrderDto> orderDtos = orderRepository.fetchJoinFinal();
        return new Result<>(orderDtos);

    }



}
