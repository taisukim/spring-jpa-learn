package com.jpabook.jpashop.dto.request.member;

import com.jpabook.jpashop.dto.request.AddressRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignupRequest {

    @NotBlank
    private String name;

    private AddressRequest address;

}
