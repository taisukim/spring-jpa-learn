package com.jpabook.jpashop.api;

import com.jpabook.jpashop.dto.Result;
import com.jpabook.jpashop.dto.response.category.CategoryResponse;
import com.jpabook.jpashop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/category")
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping("/v1/category")
    public Result<List<CategoryResponse>> findAllCategory() {
        return categoryService.findAllCategory();
    }

//    @PostMapping("/v1/category")
//    public Result<>
}
