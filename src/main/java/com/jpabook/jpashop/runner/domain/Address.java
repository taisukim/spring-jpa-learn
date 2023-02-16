package com.jpabook.jpashop.runner.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //jpa 의 내장타입이라는 뜻으로 어딘가에 내장이 될 수 있따는걸 정의하는것같음
@Getter
public class Address {

    private String city;
    private String street;
    private String zipCode;
}
