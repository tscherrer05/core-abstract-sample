package com.example.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmbeddedDatabaseAdapter implements DatabaseAdapter {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ShoeEntity> getAllShoes() {
        return jdbcTemplate.query("select * from shoe", new ShoeRowMapper());
    }
}
