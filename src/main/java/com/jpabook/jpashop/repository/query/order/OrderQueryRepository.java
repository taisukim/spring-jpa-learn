package com.jpabook.jpashop.repository.query.order;


import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.QMember;
import com.jpabook.jpashop.domain.QOrder;
import com.jpabook.jpashop.domain.QOrderItem;
import com.jpabook.jpashop.domain.item.QItem;
import com.jpabook.jpashop.dto.request.order.ReadOrderRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;


    public List<Order> findByMemberAndOrderAndItem(ReadOrderRequest request) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QOrder order = QOrder.order;
        QOrderItem orderItem = QOrderItem.orderItem;
        QItem item = QItem.item;
        QMember member = QMember.member;

        return query
                .select(order)
                .from(order)
                .join(order.member, member)
                .on(member.id.eq(request.getMemberId()))
                .join(order.orderItems, orderItem)
                .fetchJoin()
                .join(orderItem.item, item)
                .fetchJoin()
                .fetch();
    }

}
