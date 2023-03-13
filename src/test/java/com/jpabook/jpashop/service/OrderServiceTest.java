package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.domain.item.Album;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.dto.request.AddressRequest;
import com.jpabook.jpashop.dto.request.member.SignupRequest;
import com.jpabook.jpashop.dto.request.order.CreateOrderItemRequest;
import com.jpabook.jpashop.dto.request.order.CreateOrderRequest;
import com.jpabook.jpashop.repository.ItemRepository;
import com.jpabook.jpashop.repository.OrderItemRepository;
import com.jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderRepository orderRepository;

    /**
     * OrderService 의 order method 가 잘 작동하는지와
     * member 의 주소와 order 의 delivery 주소가 다르게 설정이 되는지와
     * orderItem 이 잘 생성되는지 확인하기위한 테스트
     */
    @Test
    void orderTest() {
        SignupRequest signupRequest = new SignupRequest();
        AddressRequest addressRequest = getAddressRequest("seoul", "sungsoo", "132-133");
        signupRequest.setName("taesoo");
        signupRequest.setAddress(addressRequest);
        memberService.signup(signupRequest);

        Item item1 = Album.createAlbum("1", 1000, 10, "1", "1");
        Item item2 = Book.createBook("2", 1000, 10, "2", "2");
        Item item3 = Album.createAlbum("3", 1000, 10, "3", "3");
        Item item4 = Book.createBook("4", 1000, 10, "4", "4");

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        itemRepository.save(item4);

        List<Item> items1 = itemRepository.findAll();
        System.out.println(items1);

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setMemberId(1L);
        createOrderRequest.setAddress(getAddressRequest("anyang", "manangu", "555-11"));
        List<CreateOrderItemRequest> itemRequests = new ArrayList<>();
        itemRequests.add(makeCreateOrderItemRequest(item1.getId(), 10));
        itemRequests.add(makeCreateOrderItemRequest(item2.getId(), 10));
        itemRequests.add(makeCreateOrderItemRequest(item4.getId(), 5));
        createOrderRequest.setItems(itemRequests);

        orderService.order(createOrderRequest);
        List<Order> byMemberName = orderRepository.findByMemberName(signupRequest.getName());
        Order order = byMemberName.get(0);

        List<OrderItem> items = orderItemRepository.findAll();
        assertEquals(3, items.size());
        assertEquals("anyang", order.getDelivery().getAddress().getCity());
        assertEquals("seoul", order.getMember().getAddress().getCity());
    }

    private CreateOrderItemRequest makeCreateOrderItemRequest(Long id, int count) {
        CreateOrderItemRequest createOrderItemRequest = new CreateOrderItemRequest();
        createOrderItemRequest.setItemId(id);
        createOrderItemRequest.setCount(count);
        return createOrderItemRequest;
    }

    private AddressRequest getAddressRequest(String city, String street, String zipcode) {
        AddressRequest addressRequest = new AddressRequest() {
            @Override
            public void setCity(String city) {
                super.setCity(city);
            }

            @Override
            public void setStreet(String street) {
                super.setStreet(street);
            }

            @Override
            public void setZipcode(String zipcode) {
                super.setZipcode(zipcode);
            }
        };
        addressRequest.setCity(city);
        addressRequest.setStreet(street);
        addressRequest.setZipcode(zipcode);
        return addressRequest;
    }
}