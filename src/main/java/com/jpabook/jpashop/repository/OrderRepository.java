package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Order order) {
        em.persist(order);
        return order.getId();
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    public List<Order> findAll() {
        return em.createQuery("select o from Order o " +
                        "join fetch o.member m " +
                        "join fetch o.orderItems oi " +
                        "join fetch oi.item i ", Order.class)
                .getResultList();
    }

    public List<Order> findByMemberName(String name) {
        return em.createQuery("select o from Order o join fetch o.member m where m.name = :name", Order.class)
                .setParameter("name", name)
                .getResultList();
    }
}
