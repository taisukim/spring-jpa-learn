package com.jpabook.jpashop.dto.request.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderItemRequest {
    private Long itemId;
    private int stockQuantity;
    private int count;
}
