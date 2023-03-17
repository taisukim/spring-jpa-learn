package com.jpabook.jpashop.domain.item;

import com.jpabook.jpashop.domain.Category;
import com.jpabook.jpashop.dto.request.item.ItemRequest;
import com.jpabook.jpashop.dto.request.order.CreateOrderItemRequest;
import com.jpabook.jpashop.exception.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
//inheritance = 계승
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//discriminator = 판별기
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Column(unique = true)
    private String name;
//    private String itemType;
    private int price;
    private int stockQuantity;

    // DiscriminatorColumn 값을 객체에 추가하고 싶으면
    // updatable, insertable 의 값을 false 로 설정해줘야 가능하다 안하면 오류남 ㅎ
    @Column(updatable = false, insertable = false)
    protected String dtype;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    protected Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    protected Item(ItemRequest itemRequest) {
        this.name = itemRequest.getName();
        this.price = itemRequest.getPrice();
        this.stockQuantity = itemRequest.getStockQuantity();
    }


    public int countCheck(int quantity) {
        int count = this.stockQuantity - quantity;
        if (count < 0 ) {
            throw new NotEnoughStockException("구매수량이 재고수량을 초과합니다.");
        }
        this.stockQuantity = count;
        return quantity;
    }
}
