package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.dto.request.member.SignupRequest;
import com.jpabook.jpashop.dto.response.member.FindAllMemberResponse;
import com.jpabook.jpashop.exception.ExistUserException;
import com.jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long signup(SignupRequest signupRequest) {
        validateDuplicateMember(signupRequest);

        Member member = Member.createMember(signupRequest);
        memberRepository.save(member);
        return member.getId();
    }

    public FindAllMemberResponse findMembers() {
        List<Member> members = memberRepository.findAll();
        FindAllMemberResponse memberResponses = new FindAllMemberResponse(members);
        return memberResponses;
    }

    private void validateDuplicateMember(SignupRequest signupRequest) {
        List<Member> existMember = memberRepository.findByName(signupRequest.getName());
        if(!existMember.isEmpty()){
            throw new ExistUserException("이미 존재하는 회원입니다");
        }
    }
}
