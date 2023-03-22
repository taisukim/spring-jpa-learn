package com.jpabook.jpashop.dto.request;

import com.jpabook.jpashop.domain.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
    private String city;
    private String street;
    private String zipcode;

    public Address toAddress() {
        return new Address(this);
    }
}
