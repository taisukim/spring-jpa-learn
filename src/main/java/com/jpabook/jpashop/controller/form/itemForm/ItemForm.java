package com.jpabook.jpashop.controller.form.itemForm;

import com.jpabook.jpashop.domain.item.Album;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.domain.item.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@ToString
public abstract class ItemForm {
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    private String itemType;

    ItemForm(Item item){
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        if(item instanceof Book){
            this.itemType = "BOOK";
        }else if(item instanceof Movie){
            this.itemType = "MOVIE";
        }else if(item instanceof Album){
            this.itemType = "ALBUM";
        }
    }

    ItemForm(FindItemForm form) {
        this.id = form.getId();
        this.name = form.getName();
        this.price = form.getPrice();
        this.stockQuantity = form.getStockQuantity();
        if(this instanceof BookForm){
            this.itemType = "BOOK";
        }else if(this instanceof MovieForm){
            this.itemType = "MOVIE";
        }else if(this instanceof AlbumForm){
            this.itemType = "ALBUM";
        }
    }


}
