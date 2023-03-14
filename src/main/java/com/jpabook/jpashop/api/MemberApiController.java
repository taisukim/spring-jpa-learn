package com.jpabook.jpashop.api;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.dto.request.CreateMemberRequest;
import com.jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/v1/members")
    public List<Member> membersV1(){
        List<Member> members = memberService.findMembers();
        return members;
    }

    @GetMapping("/v2/members")
    public Result<List<MemberDto>> memberV2(){
        List<Member> members = memberService.findMembers();
        List<MemberDto> collect = members.stream().map(member -> new MemberDto(member.getName()))
                .collect(Collectors.toList());
        return new Result<>(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }


    @PostMapping("/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest req) {
        Member member = new Member();
        member.setName(req.getName());
        Long memberId = memberService.join(member);
        return new CreateMemberResponse(memberId);
    }

    @PutMapping("/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id
            , @RequestBody @Valid UpdateMemberRequest request
    ) {
        memberService.update(id, request.getName());
        Member member = memberService.findMember(id);
        return new UpdateMemberResponse(member.getId(), member.getName());
    }

    @Data
    static class UpdateMemberRequest{
        private String name;
    }
    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
