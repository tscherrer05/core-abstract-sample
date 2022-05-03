package com.example.demo.core;

import com.example.demo.dto.in.ShoeFilter;

import java.math.BigInteger;
import java.util.List;

public interface DatabaseAdapter {
    List<ShoeEntity> getAllShoes();

    int countShoes();

    void saveShoe(ShoeFilter.Color color, BigInteger size);

    void removeShoe(ShoeFilter.Color color, BigInteger size);
}
