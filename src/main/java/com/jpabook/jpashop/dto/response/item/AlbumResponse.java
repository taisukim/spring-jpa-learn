package com.jpabook.jpashop.dto.response.item;

import com.jpabook.jpashop.domain.item.Album;
import lombok.Data;

@Data
public class AlbumResponse extends ItemResponse{

    private String artist;
    private String ect;

    private AlbumResponse(Album album) {
        super(album);
        this.artist = album.getArtist();
        this.ect = album.getEct();
    }

    public static AlbumResponse createAlbumResponse(Album album) {
        return new AlbumResponse(album);
    }
}
