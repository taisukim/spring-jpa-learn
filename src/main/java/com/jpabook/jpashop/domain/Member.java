package com.jpabook.jpashop.domain;

import com.jpabook.jpashop.dto.request.member.SignupRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @Embedded
    private Address address;

    // mappedBy 값 기준은 Order 클래스에서 Member 클래스를 사용하는 필드의 이름을 적으면 된다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    /**
     * member 객체 생성용 static method 호출용 생성자
     * @param signupRequest
     */
    private Member(SignupRequest signupRequest) {
        this.name = signupRequest.getName();
        this.address = new Address(signupRequest);
    }

    /**
     * Member 생성용 method
     * @param signupRequest
     * @return
     */
    public static Member createMember(SignupRequest signupRequest) {
        return new Member(signupRequest);
    }



//    /**
//     * test용으로 만듦
//     */
//    //TODO delete
//    public static Member onlyUseTest(String name){
//        return new Member(name);
//    }
//
//    private Member(String name) {
//        this.name = name;
//    }
//    @Override
//    protected void finalize() throws Throwable {
//        super.finalize();
//    }
}
