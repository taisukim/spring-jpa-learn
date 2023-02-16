package com.jpabook.jpashop.runner.domain;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.matcher.FilterableList;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    /**
     * mappedBy 는 이 연관관계의 주인이 아니다라는걸 정의하는것같음
     * Member 객체에서는 Order 라는 객체를 변경할수 없다는걸 정의하는것같
     */
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
