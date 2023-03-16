package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.*;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.dto.response.order.SimpleOrderDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }


    /**
     * jpa 가 제공하는 criteria 라는건데 쓰기 굉장히 복잡해보임.
     * 어떤식으로 사용하는건지 참고하기위해 넣음.
     * @param orderSearch
     * @return
     */
    public List<Order> solveCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }

    /**
     * jpql 로 String 쿼리를 빡쎄게 조립하는방식
     * @param orderSearch
     * @return
     */
    public List<Order> solveByJpql(OrderSearch orderSearch){
        String jpql = "select o from Order o join o.member m ";
        boolean isFirstCondition = true;

        if (orderSearch.getOrderStatus() != null) {
            if(isFirstCondition){
                jpql += " where ";
                isFirstCondition = false;
            }else{
                jpql += " and ";
            }
            jpql += " o.status = :status ";
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where ";
                isFirstCondition = false;
            } else {
                jpql += " and ";
            }
            jpql += " m.name like :name ";
        }
        TypedQuery<Order> orderTypedQuery = em.createQuery(jpql, Order.class)
                .setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            orderTypedQuery.setParameter("status", orderSearch.getOrderStatus());
        }
        if(StringUtils.hasText(orderSearch.getMemberName())){
            orderTypedQuery.setParameter("name", orderSearch.getMemberName());
        }

        return orderTypedQuery.getResultList();


    }

    public List<Order> findAllDsl(OrderSearch orderSearch) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QOrder order = QOrder.order;
        QMember member = QMember.member;
        QDelivery delivery = QDelivery.delivery;

        return query
                .select(order)
                .from(order)
                .join(order.member, member)
                .fetchJoin()
                .join(order.delivery, delivery)
                .fetchJoin()
                .where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch.getMemberName()))
                .limit(1000)
                .fetch();
    }

    private BooleanExpression nameLike(String memberName) {
        if (!StringUtils.hasText(memberName)) {
            return null;
        }
        return QMember.member.name.like(memberName);
    }

    private BooleanExpression statusEq(OrderStatus orderStatus) {
        if (orderStatus == null) {
            return null;
        }
        return QOrder.order.status.eq(orderStatus);
    }

    /**
     * 이거는 orderSearch 안에 값이 하나라도 없다면 에러발생함.
     * @param orderSearch
     * @return
     */
    public List<Order> findAll(OrderSearch orderSearch){
        return em.createQuery("select o from Order o join o.member m" +
                                " where o.status = :status " +
                                " and m.name like :name "
                        , Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
//                이렇게하면 100번째부터 가져와서 아래 maxResults 값 만큼 데이터를 가져옴 이걸로 페이징하면 된다고함
//                .setFirstResult(100)
                .setMaxResults(1000)
                .getResultList();
    }

    /**
     * fetch join 을 이용한 N+1 문제 해결
     * @return
     */
    public List<Order> orderFetchJoin() {
        return em.createQuery("select o from Order o " +
                        "join fetch o.member " +
                        "join fetch o.delivery", Order.class)
                .getResultList();
    }

    /**
     * select 하는 컬럼까지 극한으로 최적화하는법
     * @return
     */
    public List<SimpleOrderDto> orderFetchJoinFinal() {
        return em.createQuery(
        "select new com.jpabook.jpashop.dto.response.order.SimpleOrderDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                "from Order o " +
                "join o.member m " +
                "join o.delivery d ", SimpleOrderDto.class)
                .getResultList();
    }

    /**
     * fetch join 을 모두 하지만 이 메서드는 페이징이 불가능함.
     * @return
     */
    public List<Order> findAllOrderItem() {
        return em.createQuery(
                "select distinct o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d" +
                        " join fetch  o.orderItems oi" +
                        " join fetch oi.item i", Order.class)
                .getResultList();
    }

    public List<Order> findAllWithItemV2() {
        return em.createQuery("select o from Order o " +
                        "join fetch o.member m " +
                        "join fetch o.delivery d ", Order.class)
                .getResultList();
    }

    /**
     * jpa 의 default batch fetch size 설정을 해주면 in query 로 조인된 테이블을 조회해서 집어넣는다.
     * 이방법은 페이징이 가능하고 데이터량이 많으면 적은것보다 빠를수 있다.
     * @param offset
     * @param limit
     * @return
     */
    public List<Order> findAllWithItemV3Page(int offset, int limit) {
        return em.createQuery("select o from Order o ", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
