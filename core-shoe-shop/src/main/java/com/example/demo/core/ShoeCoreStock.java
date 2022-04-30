package com.example.demo.core;

import com.example.demo.dto.in.ShoeFilter;
import com.example.demo.dto.in.ShoeFilter.Color;
import com.example.demo.dto.out.Shoe;
import com.example.demo.dto.out.Shoes;
import com.example.demo.dto.out.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigInteger;
import java.util.List;

@Implementation(version = 3)
public class ShoeCoreStock extends AbstractShoeCore {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Shoes search(final ShoeFilter filter) {
        return Shoes.builder()
                .shoes(List.of(Shoe.builder()
                        .name("New shoe")
                        .color(filter.getColor().orElse(Color.BLACK))
                        .size(filter.getSize().orElse(BigInteger.TWO))
                        .build()))
                .build();
    }

    public Stock get() {
        var shoes = jdbcTemplate.query("select * from shoe", new ShoeRowMapper());

        return Stock.builder()
                .state(Stock.State.SOME)
                .shoes(List.of(Shoe.builder()
                        .name("Nike")
                        .size(BigInteger.TWO)
                        .color(Color.BLACK)
                        .build()))
                .build();
    }
}
