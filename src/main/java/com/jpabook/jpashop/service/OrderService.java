package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Delivery;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.dto.Result;
import com.jpabook.jpashop.dto.request.order.CreateOrderRequest;
import com.jpabook.jpashop.dto.request.order.ReadOrderRequest;
import com.jpabook.jpashop.dto.response.order.OrderResponse;
import com.jpabook.jpashop.repository.*;
import com.jpabook.jpashop.repository.query.order.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;
    private final DeliveryRepository deliveryRepository;

    @Transactional
    public OrderResponse order(CreateOrderRequest request) {
        Member member = memberRepository.findOne(request.getMemberId());
        if (member == null) {
            throw new IllegalArgumentException("해당 회원이 존재하지 않습니다");
        }
        List<Item> items = itemRepository.findList(request.getItemIdList());
        Order order = Order.createOrder(member);
        orderRepository.save(order);
        List<OrderItem> orderItemList = OrderItem.createOrderItem(order, items, request.getItems());
        orderItemRepository.saveAll(orderItemList);
        Delivery delivery = Delivery.createDelivery(order, request);
        deliveryRepository.save(delivery);
        order.setDelivery(delivery);

        return new OrderResponse();

    }

    public Result<List<OrderResponse>> findOrders(ReadOrderRequest request, PageRequest pageRequest) {
        List<Order> orders = orderQueryRepository.findByMemberAndOrderAndItem(request, pageRequest);
        List<OrderResponse> orderResponses = orders.stream().map(OrderResponse::new).collect(Collectors.toList());
        return new Result<>(orderResponses);
    }

    public Result<List<OrderResponse>> findAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponses = orders.stream().map(OrderResponse::new).collect(Collectors.toList());
        return new Result<>(orderResponses);
    }
}












