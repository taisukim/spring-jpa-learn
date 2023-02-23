package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.controller.form.itemForm.*;
import com.jpabook.jpashop.domain.item.Album;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.domain.item.Movie;
import com.jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(FindItemForm type) {
        Item item = type.find();
        itemService.saveItem(item);
        return "redirect:/";
    }


    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";

    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Item item = itemService.findOne(itemId);
//        System.out.println(item);
        System.out.println("this is get");
        return findItemType(model, item);
    }

    private String findItemType(Model model, Item item) {
        ItemForm form = item.getForm();
        model.addAttribute("form", form);
        return "items/updateItemForm";
    }


    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") FindItemForm form){
        System.out.println(form);
        form.setItemType("BOOK");
        System.out.println("this is post");
        Item item = form.find();
        itemService.saveItem(item);
        return "redirect:/items";

    }
}
