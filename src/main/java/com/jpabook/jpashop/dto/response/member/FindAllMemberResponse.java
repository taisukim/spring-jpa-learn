package com.jpabook.jpashop.dto.response.member;

import com.jpabook.jpashop.domain.Member;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FindAllMemberResponse {

    private final int count;
    private final List<MemberInfo> members;

    public FindAllMemberResponse(List<Member> members){
        this.count = members.size();
        this.members = members.stream().map(MemberInfo::new).collect(Collectors.toList());
    }

    @Data
    static class MemberInfo{

        private final String name;
        private final String city;
        private final String street;
        private final String zipcode;

        public MemberInfo(Member member) {
            this.name = member.getName();
            this.city = member.getAddress().getCity();
            this.street = member.getAddress().getStreet();
            this.zipcode = member.getAddress().getZipcode();
        }
    }

}
