package com.jpabook.jpashop.dto.request.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReadOrderRequest {
    private Long memberId;
    private Long orderId;
    private String itemName;
}
