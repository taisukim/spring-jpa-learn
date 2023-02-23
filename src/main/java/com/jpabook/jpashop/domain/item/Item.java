package com.jpabook.jpashop.domain.item;

import com.jpabook.jpashop.controller.form.itemForm.ItemForm;
import com.jpabook.jpashop.domain.Category;
import com.jpabook.jpashop.exception.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
/**
 * SINGLE_TABLE : 이 부모테이블에 모든 자식테이블의 정보를 같이 담는다
 * JOINED : Item 테이블과 모든 자식테이블을 다 생성하고 pk 로 묶는다 ?
 */
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Getter @Setter
@NoArgsConstructor
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    /**
     * business logic
     * 재고수량 증가
     * @param quantity
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     * @param quantity
     */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }


    Item(ItemForm form){
        this.id = form.getId();
        this.name = form.getName();
        this.price = form.getPrice();
        this.stockQuantity = form.getStockQuantity();
    }

//    protected static Item createItem();

    /**
     * 컨트롤러에서 뷰단에 뿌릴 객체로 편하게 변경하기위해 만듬
     * @return
     */
    public abstract ItemForm getForm();

    /**
     *  Item 의 자식객체들을 확인하기위한 메서드
     */

}

