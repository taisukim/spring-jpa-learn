package com.jpabook.jpashop.runner.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") // 정확하게는 모르겠음 뭐하는건지 대충 Item 이라는 객체를 상속받는 애들을 구분하기위해서 만드는것같
@Getter @Setter
public class Book extends Item{

    private String author;
    private String isbn;


}
