package com.jpabook.jpashop.controller.form.itemForm;

import com.jpabook.jpashop.domain.item.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MovieForm extends ItemForm {

    private String director;
    private String actor;

    public MovieForm(Movie movie) {
        super(movie);
        this.director = movie.getDirector();
        this.actor = movie.getActor();
    }

    public MovieForm(FindItemForm form) {
        super(form);
        this.director = form.getDirector();
        this.actor = form.getActor();
    }

}
