package com.jpabook.jpashop.runner.domain.item;

import com.jpabook.jpashop.runner.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
/**
 * SINGLE_TABLE : 이 부모테이블에 모든 자식테이블의 정보를 같이 담는다
 * JOINED : Item 테이블과 모든 자식테이블을 다 생성하고 pk 로 묶는다 ?
 */
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "book")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();
}

