package com.jpabook.jpashop.domain.item;

import com.jpabook.jpashop.controller.form.itemForm.ItemForm;
import com.jpabook.jpashop.controller.form.itemForm.MovieForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
@Getter @Setter
@NoArgsConstructor
public class Movie extends Item{

    private String director;
    private String actor;


    private Movie(MovieForm form){
        super(form);
        this.director = form.getDirector();
        this.actor = form.getActor();
    }

    public static Movie createMovie(MovieForm form) {
        return new Movie(form);
    }

    @Override
    public MovieForm getForm() {
        return new MovieForm(this);
    }

}
