package com.example.demo.dto.out;

import com.example.demo.dto.in.ShoeFilter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;

@Value
@Builder
@JsonDeserialize(builder = Category.class)
public class Category {
    Shoe shoe;
    Integer quantity;

    public ShoeFilter.Color getColor() {
        return shoe.getColor();
    }

    public String getName() {
        return shoe.getName();
    }

    public BigInteger getSize() {
        return shoe.getSize();
    }

    public Integer getQuantity() {
        return quantity;
    }
}
