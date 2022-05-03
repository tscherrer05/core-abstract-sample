package com.example.demo.dto.in;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;

@Value
@Builder
@JsonDeserialize(builder = StockUpdate.StockUpdateBuilder.class)
public class StockUpdate {

    public BigInteger size;
    public ShoeFilter.Color color;
    public int quantity;

    @JsonPOJOBuilder(withPrefix = "")
    public static class StockUpdateBuilder {

    }
}
