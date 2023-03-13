package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.item.Album;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NonUniqueResultException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional(readOnly = true)
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    /**
     * 이거는 EntityManager 에서 find 메서드가 Optional 안에
     * 잘 들어가는지 확인해보려고 테스트코드를 작성해보았따.
     */
    @Test
    @Transactional
    void itemOptionalTest() {
        Item item = Album.createAlbum("album", 1000, 10, "taesoo", "ect");
        itemRepository.save(item);
        Optional<Item> existAlbum = itemRepository.findOne(item.getId());
        Item album = null;
        if(existAlbum.isPresent()){
            album = existAlbum.get();
        }
        Assertions.assertEquals("album", album.getName());
    }

    /**
     * repository 에서 getSingleResult 로 찾았을경우
     * 만약 결과가 2개면 어떤식으로 에러가 발생되는지 또는 에러가 발생되지 않는다면 어떤식으로 작동되는지
     * 확인하려고 테스트코드를 작성해보았다.
     *
     * 추가로 중복예외를 처리하는 테스트메서드를 30분정도 찾아봤지만 찾지못했다.
     * 사실 이 테스트코드에서 발생하는 에러는 domain 에 unique 를 설정하면 발생하지 않을것이다
     * 굳이 어거지로 예외를 발생시키는걸 테스트로 작성하는게 맞나 싶다..
     * 아마 찾아보면 중복예외를 처리하는 메서드가 있긴할것같은데 졸령
     */
    @Test
    @Transactional
    void getSingleResultTest() {
        Item item = Album.createAlbum("album", 1000, 10, "taesoo", "ect");
        Item item2 = Album.createAlbum("album", 2000, 20, "taesoo2", "ect2");
        itemRepository.save(item);
        itemRepository.save(item2);

//        Assertions.assertThrows(IncorrectResultSizeDataAccessException.class, () -> itemRepository.findByName(item.getName()));
        assertThatThrownBy(() -> itemRepository.findByName(item.getName()))
                .isInstanceOf(NonUniqueResultException.class)
                .isInstanceOf(IncorrectResultSizeDataAccessException.class);

        Optional<Item> existItem = itemRepository.findByName(item.getName());
        Item album = null;
        if (existItem.isPresent()) {
            album = existItem.get();
        }
        System.out.println(album.getName());

    }

    @Test
    @Transactional
    void checkFindList() {
        Item album = Album.createAlbum("album", 1000, 10, "taesoo", "ect");
        Item book = Book.createBook("book", 1000, 10, "taesoo", "ect");
        itemRepository.save(album);
        itemRepository.save(book);

        List<Long> idList = new ArrayList<>();
        idList.add(album.getId());
        idList.add(book.getId());

        List<Item> itemList = itemRepository.findList(idList);
        System.out.println(itemList.toString());
    }

    /**
     * 아이템 테이블을 불러올때 기준이되는 List 안에 순서대로 불러올수 있는지 확인하는 테스트
     * mysql 에는 field 라는 함수가 존재해서 가능했지만
     * h2 에는 field 라는 함수기능이 따로 존재하지않아 힘들것같다
     */
    @Test
    @Transactional
    void findListSortingTest() {
        Item item1 = Album.createAlbum("1", 1000, 10, "1", "1");
        Item item2 = Book.createBook("2", 1000, 10, "2", "2");
        Item item3 = Album.createAlbum("3", 1000, 10, "3", "3");
        Item item4 = Book.createBook("4", 1000, 10, "4", "4");

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        itemRepository.save(item4);

        List<Long> idList = new ArrayList<>();
        idList.add(3L);
        idList.add(2L);
        idList.add(1L);
        idList.add(4L);
        List<Item> list = itemRepository.findList(idList);

        System.out.println(idList);
        System.out.println(list);

    }
}