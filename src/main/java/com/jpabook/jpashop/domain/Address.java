package com.jpabook.jpashop.domain;

import com.jpabook.jpashop.dto.request.AddressRequest;
import com.jpabook.jpashop.dto.request.member.SignupRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address(SignupRequest signupRequest) {
        this.city = signupRequest.getAddress().getCity();
        this.street = signupRequest.getAddress().getStreet();
        this.zipcode = signupRequest.getAddress().getZipcode();
    }

    public Address(AddressRequest addressRequest) {
        this.zipcode = addressRequest.getZipcode();
        this.street = addressRequest.getStreet();
        this.city = addressRequest.getCity();
    }

}
