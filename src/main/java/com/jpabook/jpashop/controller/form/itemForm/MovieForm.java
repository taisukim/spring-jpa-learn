package com.jpabook.jpashop.controller.form.itemForm;

import com.jpabook.jpashop.controller.form.itemForm.ItemForm;
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
        this.setId(book.getId());
        this.setName(book.getName());
        this.setPrice(book.getPrice());
        this.setStockQuantity(book.getStockQuantity());
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
    }
}
