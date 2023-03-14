package com.jpabook.jpashop.runner;

import com.jpabook.jpashop.domain.*;
import com.jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomRunner implements ApplicationRunner {
    private final EntityManager em;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============================================");
        System.out.println("============ Runner is here ================");
        System.out.println("============================================");
        init1();
        init2();
//        try(Connection connection = dataSource.getConnection()){
//            log.info(connection.getMetaData().getURL());
//        }
    }
    public void init1(){
        Member member = createMember("userA", "cityA", "streetA", "111");
        em.persist(member);

        Book book1 = createBook("JPA1 BOOK", 10000, 100);
        em.persist(book1);

        Book book2 = createBook("JPA2 BOOK", 20000, 200);
        em.persist(book2);

        OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 2);
        OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 3);

        Delivery delivery = createDelivery(member);
        Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
        em.persist(order);
    }

    public void init2(){
        Member member = createMember("userB", "cityB", "streetB", "222");
        em.persist(member);

        Book book1 = createBook("SPRING1 BOOK", 30000, 300);
        em.persist(book1);

        Book book2 = createBook("SPRING2 BOOK", 40000, 400);
        em.persist(book2);

        OrderItem orderItem1 = OrderItem.createOrderItem(book1, 30000, 4);
        OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 5);

        Delivery delivery = createDelivery(member);
        Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
        em.persist(order);
    }

    private Member createMember(String name, String city, String street, String zipCode) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address(city, street, zipCode));
        return member;
    }

    private Delivery createDelivery(Member member) {
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        return delivery;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        return book;
    }
}
