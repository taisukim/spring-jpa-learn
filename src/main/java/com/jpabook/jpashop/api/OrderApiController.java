package com.jpabook.jpashop.api;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.repository.OrderSearch;
import com.jpabook.jpashop.repository.order.query.OrderDtoV6;
import com.jpabook.jpashop.repository.order.query.OrderItemQueryDto;
import com.jpabook.jpashop.repository.order.query.OrderQueryDto;
import com.jpabook.jpashop.repository.order.query.OrderQueryRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    /**
     * V1 : 엔티티를 그대로 보냄
     * 장점 : 간편함.
     * 단점 : 쿼리가 많이 나갈수 있고 테이블을 변경할시 API 스펙이 변경돼서 유지보수가 힘들수 있음
     * @return
     */
    @GetMapping("/v1/orders")
    public List<Order> orderV1() {
        List<Order> all = orderRepository.solveByJpql(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(orderItem -> orderItem.getItem().getName());
        }
        return all;
    }

    /**
     * DTO 를 만들어서 조회후 에 DTO로 변환
     * 장점 : 간편하고 테이블을 수정하더라고 스펙이 변경되지 않음
     * 단점 : 쿼리가 많이나갈수 있음
     * @return
     */
    @GetMapping("/v2/orders")
    public List<OrderDto> ordersV2(){
        List<Order> orders = orderRepository.solveByJpql(new OrderSearch());
        return orders.stream().map(OrderDto::new).collect(Collectors.toList());

    }

    /**
     * fetch join 으로 쿼리 최적화
     * 장점 : 한방쿼리가 나가서 성능이 좋음
     * 단점 : fetch join 의 ToMany가 존재한다면 페이징이 불가능함.
     */
    @GetMapping("/v3/orders")
    public List<OrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllOrderItem();
        return orders.stream().map(OrderDto::new).collect(Collectors.toList());
    }

    /**
     * hibernate.default_batch_fetch_size 설정하여 ToMany 에는
     * fetch join 을 사용하지않고 in 을 사용한 쿼리가 나감
     * 장점 : 페이징이 가능하고 데이터량에따라 성능이 좋을수 있음
     * 단점 : ???
     */
    @GetMapping("/v3.1/orders")
    public List<OrderDto> ordersV3_page(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "100") int limit){
        List<Order> orders = orderRepository.findAllWithItemV3Page(offset, limit);
        return orders.stream().map(OrderDto::new).collect(Collectors.toList());
    }

    /**
     * 데이터를 조회할때 Entity 객체로 조회하는게 아닌 DTO 로 바로 조회하여
     * Select 절을 최소로 줄일수 있음
     * 장점 : 성능이 좋음
     * 단점 : ToMany 가 있을경우 N + 1 문제가 발생할수 있음
     */
    @GetMapping("/v4/orders")
    public List<OrderQueryDto> ordersV4(){
        return orderQueryRepository.findOrderQueryDtosV4();
    }

    /**
     * V4 의 N + 1 문제를 보완하여 Root Query 의 결과로 다시
     * 쿼리를 작성하여 N+1 문제 해결함
     * 장점 : V4 보다 성능이 좋음
     * 단점 : ???
     */
    @GetMapping("/v5/orders")
    public List<OrderQueryDto> ordersV5(){
        return orderQueryRepository.findOrderQueryDtoListV5();
    }

    /**
     * 쿼리를 결과 그대로 DTO 로 가져와서
     * API 에서 매핑하여 리턴
     * 장점 : 한방쿼리로 성능이 좋음
     * 단점 : ToMany 의경우 api 안에서 매핑을 해야하기때문에 코드짜기 힘듬, 페이징이 불가능함
     */
    @GetMapping("/v6/orders")
    public List<OrderQueryDto> ordersV6(){
        List<OrderDtoV6> orderDtoV6s = orderQueryRepository.findAllByDtoV6();
        return orderDtoV6s.stream()
                .collect(Collectors.groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress())
                        , Collectors.mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), Collectors.toList())
                ))
                .entrySet()
                .stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(),
                        e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
                        e.getKey().getAddress(), e.getValue()))
                .collect(Collectors.toList());

    }

    @Getter
    static class OrderDto {
        private Long orderId;
        private String memberName;
        private Address address;
        private List<OrderItemDto> orderItemList = new ArrayList<>();

        public OrderDto(Order o) {
            this.orderId = o.getId();
            this.memberName = o.getMember().getName();
            this.address = o.getDelivery().getAddress();
            this.orderItemList = o.getOrderItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
        }
    }

    @Getter
    static class OrderItemDto{
        private final String itemName;
        private int price;
        private int count;
        public OrderItemDto(OrderItem oi) {
            this.itemName = oi.getItem().getName();
            this.price = oi.getOrderPrice();
            this.count = oi.getCount();
        }
    }
}
