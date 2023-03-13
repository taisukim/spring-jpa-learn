package com.jpabook.jpashop.domain.item;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter
public class Book extends Item{

    private String author;
    private String isbn;

    private Book(String name, int price, int stockQuantity, String author, String isbn) {
        super(name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }

    public static Item createBook(String name, int price, int stockQuantity, String author, String isbn){
        return new Book(name, price, stockQuantity, author, isbn);
    }

}
