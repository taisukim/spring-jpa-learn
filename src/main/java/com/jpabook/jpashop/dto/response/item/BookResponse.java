package com.jpabook.jpashop.dto.response.item;

import com.jpabook.jpashop.domain.item.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse extends ItemResponse {

    private String author;
    private String isbn;

    private BookResponse(Book book){
        super(book);
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
    }

    public static BookResponse createBookResponse(Book book) {
        return new BookResponse(book);
    }

}
