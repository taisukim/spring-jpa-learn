package com.jpabook.jpashop.dto.request.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {

    @NotBlank
    private String name;
    @Min(1)
    private int price;
    @Min(1)
    private int stockQuantity;
    @NotEmpty
    private String dtype;

    //BOOK
    private String author;
    private String isbn;
    //ALBUM
    private String artist;
    private String ect;
}
