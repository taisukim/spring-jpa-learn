package com.jpabook.jpashop.dto.response.order;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private OrderMemberResponse member;
    private List<OrderItemResponse> items;

    public OrderResponse(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
        this.member = new OrderMemberResponse(order.getMember());
        this.items = order.getOrderItems().stream().map(OrderItemResponse::new).collect(Collectors.toList());
    }

    @Getter
    @Setter
    static class OrderMemberResponse{
        private Long memberId;
        private String name;

        public OrderMemberResponse(Member member) {
            this.memberId = member.getId();
            this.name = member.getName();
        }
    }

    @Getter
    @Setter
    static class OrderItemResponse{
        private Long itemId;
        private String dType;
        private String name;
        private int price;
        private int stockQuantity;

        public OrderItemResponse(OrderItem orderItem) {
            this.itemId = orderItem.getId();
            this.dType = orderItem.getItem().getDtype();
            this.name = orderItem.getItem().getName();
            this.price = orderItem.getOrderPrice();
            this.stockQuantity = orderItem.getItem().getStockQuantity();
        }
    }
}
