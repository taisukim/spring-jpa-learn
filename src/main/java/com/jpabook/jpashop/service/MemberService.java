package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     * @param member
     * @return
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();

    }

    private void validateDuplicateMember(Member member) {
        List<Member> existMembers = memberRepository.findByName(member.getName());
        if(!existMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }

    }

    /**
     * 회원 전체조회
     * @return
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findMember(Long id){
        return memberRepository.findOne(id);
    }


}
