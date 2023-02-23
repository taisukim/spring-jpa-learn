package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

//    //insert query가 나가는걸 보고싶다면 아래껄 쓰도록.
//    // 아니면 EntityManager 를 주입시키고 em.flush(); 를 사용하면 된다
//    @Rollback(value = false)
    @Test
    void 회원가입() throws Exception{
        Member member = new Member();
        member.setName("taesoo");

        Long savedId = memberService.join(member);

        assertEquals(member, memberRepository.findOne(savedId));

    }

    @Test
    void 중복_회원_에외() throws Exception{
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setName("taesoo");
        member2.setName("taesoo");
        memberService.join(member1);

        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals("이미 존재하는 회원입니다", illegalStateException.getMessage());
    }

}