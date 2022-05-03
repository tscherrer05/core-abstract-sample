package com.example.demo.dto.in;

import com.example.demo.dto.out.Shoe;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;

import java.math.BigInteger;

@Builder
@JsonDeserialize(builder = Shoe.ShoeBuilder.class)
public class StockUpdate {

    public BigInteger size;
    public ShoeFilter.Color color;
    public int quantity;


    @JsonPOJOBuilder(withPrefix = "")
    public static class StockUpdateBuilder {

    }
}
