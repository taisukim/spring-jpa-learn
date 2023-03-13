package com.jpabook.jpashop.domain;

import com.jpabook.jpashop.dto.request.order.CreateOrderRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @Enumerated
    private DeliveryStatus status;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    private Delivery(Order order, CreateOrderRequest cor) {
        this.status = DeliveryStatus.PROCEEDING;
        this.address = cor.getAddress().toAddress();
        this.order = order;
    }

    public static Delivery createDelivery(Order order, CreateOrderRequest cor) {
        return new Delivery(order, cor);
    }


}
