package com.jpabook.jpashop.domain.item;

import com.jpabook.jpashop.dto.request.item.ItemRequest;
import com.jpabook.jpashop.dto.response.item.ItemResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album extends Item{

    private String artist;
    private String ect;

    private Album(String name, int price, int stockQuantity, String artist, String ect) {
        super(name, price, stockQuantity);
        this.artist = artist;
        this.ect = ect;
    }

    private Album(ItemRequest itemRequest) {
        super(itemRequest);
        this.artist = itemRequest.getArtist();
        this.ect = itemRequest.getEct();
    }

    public static Item createAlbum(String name, int price, int stockQuantity, String artist, String ect){
        return new Album(name, price, stockQuantity, artist, ect);
    }

    public static Item createAlbum(ItemRequest itemRequest) {
        return new Album(itemRequest);
    }
}
