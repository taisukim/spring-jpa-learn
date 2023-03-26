package com.jpabook.jpashop.domain;

import com.jpabook.jpashop.domain.item.Item;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    private Order(Member member) {
        this.member = member;
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.ORDER;
    }

    public static Order createOrder(Member member) {
        return new Order(member);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    @PrePersist
    @PreUpdate
    public void onPersist(){
        orderDate = orderDate.truncatedTo(ChronoUnit.SECONDS);
    }


//    //TODO delete
//    public static Order onlyUseTestOrder(Member member, String name){
//        return new Order(member, name);
//    }
//
//    private Order(Member member, String name) {
//        this.name = name;
//        this.member = member;
//    }
}
