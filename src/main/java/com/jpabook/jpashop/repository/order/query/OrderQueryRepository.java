package com.jpabook.jpashop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;


    public List<OrderQueryDto> findOrderQueryDtosV4() {
        List<OrderQueryDto> orders = findOrdersV4();
        orders.forEach(o -> o.setOrderItems(findOrderItemsV4(o)));
        return orders;
    }

    private List<OrderItemQueryDto> findOrderItemsV4(OrderQueryDto o) {
        return em.createQuery("select new com.jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                        "from OrderItem oi " +
                        "join oi.item i " +
                        "on oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", o.getOrderId())
                .getResultList();
    }

    private List<OrderQueryDto> findOrdersV4() {
        return em.createQuery("select new com.jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                                "from Order o " +
                                "join o.member m " +
                                "join o.delivery d ", OrderQueryDto.class)
                .getResultList();
    }

    public List<OrderQueryDto> findOrderQueryDtoListV5() {
        //먼저 Order 조회
        List<OrderQueryDto> orders = findOrdersV4();
        // 조회한 Order 의 Id 값들만 뺴서 List 로 변환
        List<Long> orderIdList = toOrderIdList(orders);
        //변환한 리스트를 매개변수로 넘겨서 OrderItem 조회
        List<OrderItemQueryDto> orderItemList = findOrderItemList(orderIdList);
        //조회된 OrderItem 들을 Map 에 key값을 Order 의 Id 값으로 주고 Value 를 해당 Id 값인 OrderItemQueryDto 리스트로 변환
        Map<Long, List<OrderItemQueryDto>> orderItemMap = castMap(orderItemList);
        //Key 값이 Id 니까 Order 의 Id 값을 매개변수로 줘서 Value 끌어와서 넣어주고 리턴
        //어케생각했누.. 영한이형
        orders.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
        return orders;
    }

    public List<OrderDtoV6> findAllByDtoV6() {
        return em.createQuery("select new com.jpabook.jpashop.repository.order.query.OrderDtoV6(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                "from Order o " +
                "join o.member m " +
                "join o.delivery d " +
                "join o.orderItems oi " +
                "join oi.item i", OrderDtoV6.class)
                .getResultList();
    }

    private List<Long> toOrderIdList(List<OrderQueryDto> orders) {
        return orders.stream().map(OrderQueryDto::getOrderId).collect(Collectors.toList());
    }

    private Map<Long, List<OrderItemQueryDto>> castMap(List<OrderItemQueryDto> orderItemList) {
        return orderItemList.stream().collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
    }

    private List<OrderItemQueryDto> findOrderItemList(List<Long> orderIdList) {
        return em.createQuery("select new com.jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                        "from OrderItem oi " +
                        "join oi.item i " +
                        "on oi.order.id in :orderIdList", OrderItemQueryDto.class)
                .setParameter("orderIdList", orderIdList)
                .getResultList();
    }
}
