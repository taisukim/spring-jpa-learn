package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.exception.NotEnoughStockException;
import com.jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    EntityManager em;

    @Test
    void 상품_주문() {
        Member member = createMember();
        Book book = createBook("JPA1", 10);

        int orderCount = 8;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order order = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, order.getStatus(), "상품 주문시 주문상태는 ORDER");
        assertEquals(book.getId(), order.getOrderItems().get(0).getItem().getId(), "주문한 상품과 상품의 아이디값이 일치해야한다");
        assertEquals(orderCount * book.getPrice(), order.getTotalPrice(), "주문의 총 가격이 일치해야한다");
        assertEquals(2, book.getStockQuantity(), "주문 수량만큰 재고가 줄어야 한다");

    }

    @Test
    void 상품주문_재고수량초과() {
        Member member = createMember();

        Book book = createBook("JPA1", 10);

        int orderCount = 11;

        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), book.getId(), orderCount));

    }

    @Test
    void 주문취소() {
        Member member = createMember();
        Book book = createBook("JPA1", 10);

        int orderCount = 8;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order order = orderRepository.findOne(orderId);

        orderService.cancelOrder(order.getId());

        assertEquals(OrderStatus.CANCEL, order.getStatus(), "주문을 취소했으니 주문상태값이 취소가 돼야함");
        assertEquals(10, book.getStockQuantity(), "주문 취소했을경우 재고가 돌아와야한다");
    }

    private Book createBook(String name, int quantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(10000);
        book.setAuthor("taesooo");
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("seoul", "river", "123-123"));
        em.persist(member);
        return member;
    }
}