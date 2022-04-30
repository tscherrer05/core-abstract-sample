package com.example.demo.core;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShoeRowMapper implements RowMapper<Shoe> {

    @Override
    public Shoe mapRow(ResultSet rs, int rowNum) throws SQLException {
        Shoe shoe = new Shoe();

        shoe.setId(rs.getLong("SHOE_ID"));
        shoe.setName(rs.getString("SHOE_NAME"));
        shoe.setColor(rs.getString("SHOE_COLOR"));
        shoe.setSize(rs.getBigDecimal("SHOE_SIZE").toBigInteger());

        return shoe;
    }
}
