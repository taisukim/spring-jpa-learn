package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemRepository {

    private final EntityManager em;

    public void save(OrderItem orderItem) {
        em.persist(orderItem);
    }

    public void saveAll(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            em.persist(orderItem);
        }
    }

    public List<OrderItem> findAll() {
        return em.createQuery("select o from OrderItem o ", OrderItem.class)
                .getResultList();
    }
}
