package com.jpabook.jpashop.domain.item;

import com.jpabook.jpashop.controller.form.itemForm.BookForm;
import com.jpabook.jpashop.controller.form.itemForm.ItemForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") // 정확하게는 모르겠음 뭐하는건지 대충 Item 이라는 객체를 상속받는 애들을 구분하기위해서 만드는것같
@Getter @Setter
@NoArgsConstructor
public class Book extends Item{

    private String author;
    private String isbn;

    private Book(BookForm form){
        super(form);
        this.setAuthor(form.getAuthor());
        this.setIsbn(form.getIsbn());
    }
    public static Book createBook(BookForm form){
        return new Book(form);
    }

    @Override
    public BookForm getForm() {
        return new BookForm(this);
    }

}
