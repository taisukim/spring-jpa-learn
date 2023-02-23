package com.jpabook.jpashop.domain.item;

import com.jpabook.jpashop.controller.form.itemForm.AlbumForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter
@Setter
@NoArgsConstructor
public class Album extends Item {

    private String artist;

    private String etc;

    private Album(AlbumForm form) {
        super(form);
        this.artist = form.getArtist();
        this.etc = form.getEtc();
    }

    public static Album createAlbum(AlbumForm form) {
        return new Album(form);
    }

    @Override
    public AlbumForm getForm() {
        return new AlbumForm(this);
    }

}
