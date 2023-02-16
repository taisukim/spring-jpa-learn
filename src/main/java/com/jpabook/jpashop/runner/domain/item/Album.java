package com.jpabook.jpashop.runner.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter @Setter
public class ALbum extends Item{

    private String artist;

    private String etc;
}