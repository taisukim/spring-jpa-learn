package com.jpabook.jpashop.dto.request.order;

import com.jpabook.jpashop.dto.request.AddressRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    private Long memberId;
    private List<CreateOrderItemRequest> items = new ArrayList<>();
    private AddressRequest address;


    /**
     * 주문을 받으면 해당아이템들이 진짜 있는건지 확인하기위해
     * itemRepository 를 통해 조회하기 위해 조회하기 편한형태로
     * 변경하기위해 만든 메서드
     * @return
     */
    public List<Long> getItemIdList() {
        List<Long> result = new ArrayList<>();
        for (CreateOrderItemRequest item : this.items) {
            result.add(item.getItemId());
        }
        return result;
    }

}