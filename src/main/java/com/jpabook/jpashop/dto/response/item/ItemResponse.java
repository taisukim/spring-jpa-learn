package com.jpabook.jpashop.dto.response.item;

import com.jpabook.jpashop.domain.item.Album;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String dtype;

    //BOOK
    private String author;
    private String isbn;
    //ALBUM
    private String artist;
    private String ect;

    protected ItemResponse(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.dtype = item.getDtype();
    }

    public static ItemResponse createItemResponse(Item item) {
        switch (item.getDtype()){

            case "B": return BookResponse.createBookResponse((Book)item);
            case "A": return AlbumResponse.createAlbumResponse((Album)item);
        }
        throw new IllegalArgumentException("dtype 이 잘못되었습니다");
    }
}
