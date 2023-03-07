package com.jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
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
}
