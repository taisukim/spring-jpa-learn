package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {

        em.persist(item);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    public Optional<Item> findOne(Long id) {
        return Optional.of(em.find(Item.class, id));
    }

    public Optional<Item> findByName(String name) {
        return Optional.of(
                em.createQuery("select i from Item i where i.name = :name", Item.class)
                        .setParameter("name", name)
                        .getSingleResult()
        );
    }
}
