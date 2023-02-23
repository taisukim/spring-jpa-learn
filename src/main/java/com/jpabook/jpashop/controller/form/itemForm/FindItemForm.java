package com.jpabook.jpashop.controller.form.itemForm;

import com.jpabook.jpashop.domain.item.Album;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.domain.item.Movie;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindItemForm {

    // Item
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String itemType;

    //Movie
    private String director;
    private String actor;
    //Book
    private String author;
    private String isbn;
    //Album
    private String artist;
    private String etc;


    public Item find() {
        if(this.getItemType().equals("BOOK")){
            return Book.createBook(new BookForm(this));
        }if(this.getItemType().equals("ALBUM")){
            return Album.createAlbum(new AlbumForm(this));
        }if(this.getItemType().equals("MOVIE")){
            return Movie.createMovie(new MovieForm(this));
        }else{
            return null;
        }
    }
}
