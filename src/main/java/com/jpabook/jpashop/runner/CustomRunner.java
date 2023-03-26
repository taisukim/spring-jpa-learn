package com.jpabook.jpashop.runner;


import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.domain.item.Album;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.dto.request.AddressRequest;
import com.jpabook.jpashop.dto.request.member.SignupRequest;
import com.jpabook.jpashop.dto.request.order.CreateOrderItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class CustomRunner implements ApplicationRunner {

    private final EntityManager em;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 1; i < 31; i++) {
            addItem(i);
        }
    }

//    private void init1() {
//        Item album1 = Album.createAlbum("album1", 10000, 100, "artist1", "ect1");
//        Item album2 = Album.createAlbum("album2", 20000, 200, "artist2", "ect2");
//        Item book1 = Book.createBook("book1", 30000, 300, "author1", "isbn1");
//        Item book2 = Book.createBook("book2", 40000, 400, "author2", "isbn2");
//        em.persist(album1);
//        em.persist(album2);
//        em.persist(book1);
//        em.persist(book2);
//        SignupRequest signupRequest = new SignupRequest("taesoo");
//        AddressRequest addressRequest = new AddressRequest();
//        addressRequest.setZipcode("13");
//        addressRequest.setCity("anyang");
//        addressRequest.setStreet("street");
//        signupRequest.setAddress(addressRequest);
//        Member member = Member.createMember(signupRequest);
//        em.persist(member);
//        Order order = Order.createOrder(member);
//        em.persist(order);
//    }

    private void addItem(int num) {
        Item album = Album.createAlbum("album" + num, num * 100, num * 10, "artist" + num, "ect" + num);
        em.persist(album);
        Item book = Book.createBook("book" + num, num * 100, num * 10, "artist" + num, "ect" + num);
        em.persist(book);
        List<Item> items = new ArrayList<>();
        items.add(album);
        items.add(book);
        SignupRequest signupRequest = new SignupRequest("member" + num);
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setZipcode("zipcode" + num);
        addressRequest.setCity("city" + num);
        addressRequest.setStreet("street" + num);
        signupRequest.setAddress(addressRequest);
        Member member = Member.createMember(signupRequest);
        em.persist(member);
        Order order = Order.createOrder(member);
        em.persist(order);
        List<CreateOrderItemRequest> coirList = new ArrayList<>();
        coirList.add(cc(album));
        coirList.add(cc(book));
        List<OrderItem> orderItem = OrderItem.createOrderItem(order, items, coirList);
        for (OrderItem oi : orderItem) {
            em.persist(oi);
        }
    }

    private CreateOrderItemRequest cc(Item item) {
        CreateOrderItemRequest coir = new CreateOrderItemRequest();
        coir.setItemId(item.getId());
        coir.setCount(item.getStockQuantity() - 5);
        return coir;
    }


}
