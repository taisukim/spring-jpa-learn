package com.jpabook.jpashop.domain.item;

import com.jpabook.jpashop.domain.Category;
import com.jpabook.jpashop.dto.request.order.CreateOrderItemRequest;
import com.jpabook.jpashop.exception.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
//inheritance = 계슴
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//discriminator = 판별기
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
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

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    protected Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
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
