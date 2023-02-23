package com.jpabook.jpashop.service;


import com.jpabook.jpashop.domain.*;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;
import com.jpabook.jpashop.repository.MemberRepository;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = false)
    public Long order(Long memberId, Long itemId, int count){

        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        //원래는 Order 를 save 할때 안에있는 다른 객체들도 같이 persist 해야하지만
        //cascadeType.All 을 Order 객체의 컬럼값에 추가함으로써 자동으로 persist 한다
        // 이 cascadeType.All 설정은 Delivery , OrderItem 들이 Order에 의해서만 관리되기때문에 설정한다
        orderRepository.save(order);

        return order.getId();

    }

    @Transactional
    public void cancelOrder(Long orderId){

        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.solveByJpql(orderSearch);
    }




}
