package com.example.demo.core;

import lombok.Builder;

import java.math.BigInteger;

@Builder
public class ShoeEntity {

    public ShoeEntity(Long id, String name, String color, BigInteger size) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.size = size;
    }

    private Long id;
    private String name;
    private String color;
    private BigInteger size;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigInteger getSize() {
        return size;
    }

    public void setSize(BigInteger size) {
        this.size = size;
    }
}
