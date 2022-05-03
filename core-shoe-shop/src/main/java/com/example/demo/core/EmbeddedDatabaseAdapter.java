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
        var result = jdbcTemplate.queryForObject("select count(*) from shoe", Integer.class);
        return (result != null) ? result : 0;
    }

    @Override
    public int countShoes(ShoeFilter.Color color, BigInteger size) {
        var query = String.format("select count(*) from shoe where color = '%s' and size = %s", color, size);
        var result = jdbcTemplate.queryForObject(query, Integer.class);
        return (result != null) ? result : 0;
    }

    @Override
    public void saveShoe(ShoeFilter.Color color, BigInteger size) {
        var query = String.format("insert into shoe (color, size) values '%s', %s", color, size);
        jdbcTemplate.execute(query);
    }

    @Override
    public void removeShoe(ShoeFilter.Color color, BigInteger size) {
        var query = String.format("delete from shoe where id in (select id from shoe where color = '%s' and size = %s limit 1)", color, size);
        jdbcTemplate.execute(query);
    }
}
