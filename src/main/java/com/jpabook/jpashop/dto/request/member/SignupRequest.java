package com.jpabook.jpashop.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignupRequest {

    @NotBlank
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
