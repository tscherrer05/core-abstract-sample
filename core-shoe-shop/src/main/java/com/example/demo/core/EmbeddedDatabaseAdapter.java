package com.example.demo.core;

import com.example.demo.dto.in.ShoeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class EmbeddedDatabaseAdapter implements DatabaseAdapter {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ShoeEntity> getAllShoes() {
        return jdbcTemplate.query("select * from shoe", new ShoeRowMapper());
    }

    @Override
    public int countShoes() {
        return jdbcTemplate.queryForObject("select count(*) from shoe", Integer.class);
    }

    @Override
    public void saveShoe(ShoeFilter.Color color, BigInteger size) {
        var query = String.format("insert into shoe (color, size) values %s, %s", color, size);
        jdbcTemplate.execute(query);
    }

    @Override
    public void removeShoe(ShoeFilter.Color color, BigInteger size) {
        var query = String.format("delete from shoe where id in (select top(1) id from shoe where color = %s and size = %s)", color, size);
        jdbcTemplate.execute(query);
    }
}
