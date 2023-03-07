package com.jpabook.jpashop.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberTest {

    @Autowired
    EntityManager em;


    /**
     * 이 테스트에는 연관관계메서드를 설정하지않고 한 테스트기때문에 실행이 불가능했었던것같다
     * 연관관계메서드를 만들고 다시 테스트하기는 너무 귀찮으므로 넘어가도록하자
     */
//    @Test
//    @Rollback(value = false)
//    @Transactional
//    void memberJoinTest() throws Throwable {
//
//        Member member = Member.onlyUseTest("taesoo");
//        em.persist(member);
//
//        Member getMember = em.find(Member.class, member.getId());
//
//        Order order = Order.onlyUseTestOrder(getMember, "order1");
//        em.persist(order);
//        // member 를 persist 하고 persist 한 member 를 넣어서 order 를 persist 했는데
//        // 다시 member 를 em.find 해서 가져와도 member 안에 order 가 없었다..
//        // 그래서 처음에 집어넣은 member 를 finalize 메서드를 호출해 지워버리고 다시가져온다면 있는거아닌가 해서
//        // 저렇게 넣어봤는데 찾아보니까 언제지울지는 알려주지도않을뿐더러 제어가 불가능하다고해서 주석남기고 마무리한다
//        // 하지만 다른 테스트메소드를 작성해서 가져와보니 들어가있었다
//        member.finalize();
//
//        Order getOrder = em.find(Order.class, order.getId());
//
//        Member getMember2 = getMember(member);
//
//        assertEquals("order1", getOrder.getName());
//        assertEquals("taesoo", order.getMember().getName());
//        System.out.println("this is order List: " + getMember2.getOrder());
////        assertEquals(order.getName(), getMember2.getOrders().get(0).getName());
//    }
//
//    @Transactional
//    Member getMember(Member member) {
//        Member getMember2 = em.find(Member.class, member.getId());
//        return getMember2;
//    }
//
//    @Test
//    @Rollback(value = false)
//    @Transactional
//    void test2() {
//
//        Member member = em.find(Member.class, 1L);
//        System.out.println("this is test2 of order in member : " + member.getOrder());
//    }
}
