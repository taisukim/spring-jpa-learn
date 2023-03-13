package com.jpabook.jpashop.domain;

import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.dto.request.order.CreateOrderItemRequest;
import com.jpabook.jpashop.dto.request.order.CreateOrderRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;
    private int count;


    private OrderItem(Order order, Item item, int count) {
        this.order = order;
        this.item = item;
        this.orderPrice = item.getPrice();
        this.count = count;
    }
    public static List<OrderItem> createOrderItem(Order order, List<Item> items, List<CreateOrderItemRequest> coir) {
        List<OrderItem> orderItemList = new ArrayList<>();
        coir.sort(new Comparator<CreateOrderItemRequest>() {
            @Override
            public int compare(CreateOrderItemRequest o1, CreateOrderItemRequest o2) {
                return (int) (o1.getItemId() - o2.getItemId());
            }
        });
        for (int i = 0; i < items.size(); i++) {
            // 잘 정렬됐는지 확인용
            System.out.println("item : " + items.get(i));
            System.out.println("CreateOrderItemRequest : " + coir.get(i));
            OrderItem orderItem = new OrderItem(order, items.get(i), items.get(i).countCheck(coir.get(i).getCount()));
            //order 의 필드에 orderItem 추가
            order.addOrderItem(orderItem);
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

}
