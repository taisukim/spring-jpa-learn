package com.jpabook.jpashop.controller.form.itemForm;

import com.jpabook.jpashop.domain.item.Album;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AlbumForm extends ItemForm {

    private String artist;

    private String etc;

    public AlbumForm(Album album) {
        super(album);
        this.artist = album.getArtist();
        this.etc = album.getEtc();
    }

    public AlbumForm(FindItemForm form) {
        super(form);
        this.artist = form.getArtist();
        this.etc = form.getEtc();
    }
}
