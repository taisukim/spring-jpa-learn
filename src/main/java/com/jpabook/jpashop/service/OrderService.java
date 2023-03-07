package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.dto.request.order.CreateOrderRequest;
import com.jpabook.jpashop.dto.response.order.OrderResponse;
import com.jpabook.jpashop.repository.ItemRepository;
import com.jpabook.jpashop.repository.MemberRepository;
import com.jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

//    @Transactional
//    public OrderResponse order(CreateOrderRequest request) {
//        Member member = memberRepository.findOne(request.getMemberId());
//        if (member == null) {
//            throw new IllegalArgumentException("해당 회원이 존재하지 않습니다");
//        }
////        List<Item> items =
//
//    }
}
