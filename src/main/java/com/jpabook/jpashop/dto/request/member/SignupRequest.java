package com.jpabook.jpashop.dto.request.member;

import com.jpabook.jpashop.dto.request.AddressRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {

    @NotBlank
    private String name;

    private AddressRequest address;


    public SignupRequest(String name) {
        this.name = name;
    }
}
