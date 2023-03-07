package com.jpabook.jpashop.dto.request.order;

import com.jpabook.jpashop.dto.request.AddressRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    private Long memberId;
    private List<CreateOrderItemRequest> items;
    private AddressRequest address;

}