package com.jpabook.jpashop.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "이름은 필수값 입니다")
    private String name;
    private String city;
    private String street;
    private String zipCode;
}
