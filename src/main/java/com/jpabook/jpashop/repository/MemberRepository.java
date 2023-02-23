package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    //TODO Member 도 나중에 그 수정용으로 merge 를 하나 조건문에 만들어야 할듯
    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("SELECT m FROM Member m ", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
