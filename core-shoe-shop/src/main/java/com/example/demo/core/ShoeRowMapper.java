package com.example.demo.core;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShoeRowMapper implements RowMapper<ShoeEntity> {

    @Override
    public ShoeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ShoeEntity.builder()
                .id(rs.getLong("id"))
                .color(rs.getString("color"))
                .size(rs.getBigDecimal("size").toBigInteger()).build();
    }
}
