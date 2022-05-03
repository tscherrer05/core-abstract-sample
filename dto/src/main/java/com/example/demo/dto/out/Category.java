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
    ShoeFilter.Color color;
    BigInteger size;
    int quantity;
}
