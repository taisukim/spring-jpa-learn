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
        Member member = createMember();
        for (int i = 1; i < 31; i++) {
            addItem(i, member);
        }
    }


    private void addItem(int num, Member member) {
        Item album = Album.createAlbum("album" + num, num * 100, num * 10, "artist" + num, "ect" + num);
        em.persist(album);
        Item book = Book.createBook("book" + num, num * 100, num * 10, "artist" + num, "ect" + num);
        em.persist(book);
        List<Item> items = new ArrayList<>();
        items.add(album);
        items.add(book);
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

    private Member createMember() {
        SignupRequest signupRequest = new SignupRequest("member");
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setZipcode("zipcode");
        addressRequest.setCity("city");
        addressRequest.setStreet("street");
        signupRequest.setAddress(addressRequest);
        Member member = Member.createMember(signupRequest);
        em.persist(member);
        return member;
    }

    private CreateOrderItemRequest cc(Item item) {
        CreateOrderItemRequest coir = new CreateOrderItemRequest();
        coir.setItemId(item.getId());
        coir.setCount(item.getStockQuantity() - 5);
        return coir;
    }


}
