package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void wrongMemberTest() {
        Member member = memberRepository.findOne(1000000000L);
        assertNull(member);
    }
}