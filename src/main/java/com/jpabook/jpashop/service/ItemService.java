package com.jpabook.jpashop.service;


import com.jpabook.jpashop.domain.item.Album;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.dto.Result;
import com.jpabook.jpashop.dto.request.item.ItemRequest;
import com.jpabook.jpashop.dto.response.item.AlbumResponse;
import com.jpabook.jpashop.dto.response.item.BookResponse;
import com.jpabook.jpashop.dto.response.item.ItemResponse;
import com.jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;


    public Result<List<ItemResponse>> findItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemResponse> collect = items.stream().map(ItemResponse::createItemResponse)
                .collect(Collectors.toList());
        return new Result<>(collect);
    }

    @Transactional
    public Result<Long> createItem(ItemRequest itemRequest) {
        if (itemRequest.getDType().equals("A")) {
            return new Result<>(itemRepository.save(Album.createAlbum(itemRequest)));
        } else if (itemRequest.getDType().equals("B")) {
            return new Result<>(itemRepository.save(Book.createBook(itemRequest)));
        } else {
            throw new IllegalArgumentException("잘못된 아이템 타입입니다");
        }

    }
}
