package com.jpabook.jpashop.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AddressRequest {
    private String city;
    private String street;
    private String zipcode;
}
