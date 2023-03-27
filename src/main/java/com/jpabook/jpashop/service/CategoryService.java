package com.jpabook.jpashop.service;


import com.jpabook.jpashop.domain.Category;
import com.jpabook.jpashop.dto.Result;
import com.jpabook.jpashop.dto.response.category.CategoryResponse;
import com.jpabook.jpashop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Result<List<CategoryResponse>> findAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponse> collect = categoryList.stream().map(CategoryResponse::new).collect(Collectors.toList());
        return new Result<>(collect);

    }
}
