package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.item.Album;
import com.jpabook.jpashop.domain.item.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
     */
    @Test
    @Transactional
    void getSingleResultTest() {
        Item item = Album.createAlbum("album", 1000, 10, "taesoo", "ect");
        Item item2 = Album.createAlbum("album", 2000, 20, "taesoo2", "ect2");
        itemRepository.save(item);
        itemRepository.save(item2);

        Optional<Item> existItem = itemRepository.findByName(item.getName());
        Item album = null;
        if (existItem.isPresent()) {
            album = existItem.get();
        }
        System.out.println(album.getName());

    }
}