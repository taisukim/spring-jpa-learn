package com.jpabook.jpashop.runner.domain;

import com.jpabook.jpashop.runner.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class CategoryItem {

    @Id @GeneratedValue
    @Column(name = "category_item_id")
    private Long id;

    @OneToMany(mappedBy = "categoryItem")
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "categoryItem")
    private List<Category> categories = new ArrayList<>();

}
