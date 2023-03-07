package com.jpabook.jpashop.api;

import com.jpabook.jpashop.dto.request.member.SignupRequest;
import com.jpabook.jpashop.dto.response.member.FindAllMemberResponse;
import com.jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public Long signup(@RequestBody SignupRequest request) {
        return memberService.signup(request);
    }

    @GetMapping("/members")
    public FindAllMemberResponse findMembers() {
        return memberService.findMembers();
    }
}
