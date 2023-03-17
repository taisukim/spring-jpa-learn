package com.jpabook.jpashop.runner;


import com.jpabook.jpashop.domain.item.Album;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class CustomRunner implements ApplicationRunner {

    private final EntityManager em;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        init1();
    }

    private void init1() {
        Item album1 = Album.createAlbum("album1", 10000, 100, "artist1", "ect1");
        Item album2 = Album.createAlbum("album2", 20000, 200, "artist2", "ect2");
        Item book1 = Book.createBook("book1", 30000, 300, "author1", "isbn1");
        Item book2 = Book.createBook("book2", 40000, 400, "author2", "isbn2");
        em.persist(album1);
        em.persist(album2);
        em.persist(book1);
        em.persist(book2);
    }


}
