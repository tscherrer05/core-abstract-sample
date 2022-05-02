package com.example.demo.core;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShoeRowMapper implements RowMapper<ShoeEntity> {

    @Override
    public ShoeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ShoeEntity.builder()
                .id(rs.getLong("SHOE_ID"))
                .name(rs.getString("SHOE_NAME"))
                .color(rs.getString("SHOE_COLOR"))
                .size(rs.getBigDecimal("SHOE_SIZE").toBigInteger()).build();
    }
}
