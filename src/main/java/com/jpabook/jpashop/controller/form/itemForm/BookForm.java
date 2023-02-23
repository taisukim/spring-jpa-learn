package com.jpabook.jpashop.controller.form.itemForm;

import com.jpabook.jpashop.domain.item.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BookForm extends ItemForm {

    private String author;
    private String isbn;

    public BookForm(Book book) {
        super(book);
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
    }

    public BookForm(FindItemForm form){
        super(form);
        this.author = form.getAuthor();
        this.isbn = form.getIsbn();
    }

}
