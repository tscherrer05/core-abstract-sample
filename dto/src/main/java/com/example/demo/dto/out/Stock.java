package com.example.demo.dto.out;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@JsonDeserialize(builder = Stock.StockBuilder.class)
public class Stock {

    public Stock(State state, List<Category> categories) {
        this.state = state;
        this.categories = categories;
    }

    public enum State {
        EMPTY,
        FULL,
        SOME
    }

    public State     state;
    public List<Category> categories;

    @JsonPOJOBuilder(withPrefix = "")
    public static class StockBuilder {

    }
}
