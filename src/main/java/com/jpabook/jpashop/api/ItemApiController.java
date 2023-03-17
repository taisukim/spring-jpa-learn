package com.jpabook.jpashop.api;

import com.jpabook.jpashop.dto.Result;
import com.jpabook.jpashop.dto.request.item.ItemRequest;
import com.jpabook.jpashop.dto.response.item.ItemResponse;
import com.jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemApiController {

    private final ItemService itemService;

    @GetMapping("/v1/items")
    public Result<List<ItemResponse>> getItems() {
        return itemService.findItems();
    }

    @PostMapping("/v1/item")
    public Result<Long> createItem(@RequestBody @Valid ItemRequest itemRequest) {
        return itemService.createItem(itemRequest);
    }
}
