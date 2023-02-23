package com.jpabook.jpashop.persnalTest;

import com.jpabook.jpashop.controller.form.itemForm.BookForm;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void updateTest(){
        BookForm form = new BookForm();
        form.setName("book");
        form.setItemType("BOOK");
        form.setPrice(1000);
        form.setAuthor("taesoo");
        form.setStockQuantity(10);
        form.setIsbn("test");
        Book book = Book.createBook(form);
        itemRepository.save(book);

        System.out.println("저장하고 난 Book 의 name 값 : " + book.getName());

        updateItem(book);
        System.out.println("merge 를 하지않고 아이디값으로 객체를 찾아서 set만해주었을때 name값 : " + book.getName());
        String name = itemRepository.findOne(book.getId()).getName();
        System.out.println("위에꺼는 생성된 객체 그대로지만 아래꺼는 다시 찾아온거 : " + name);

        assertEquals("minsung", name);



    }

    @Transactional
    void updateItem(Book book) {
        Item item = itemRepository.findOne(book.getId());
        item.setName("minsung");

    }

}
