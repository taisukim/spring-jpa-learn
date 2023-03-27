package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Category;
import com.jpabook.jpashop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final EntityManager em;


    public List<Category> findAll() {
        return em.createQuery("select c from Category c ", Category.class)
                .getResultList();
    }
}
