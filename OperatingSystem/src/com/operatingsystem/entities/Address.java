package com.operatingsystem.entities;

public class Address {

    private Integer base;
    private Integer size;


    public Address() {
    }

    public Address(Integer base, Integer size) {
        this.base = base;
        this.size = size;
    }

    Integer getBase() {
        return base;
    }

    Integer getSize() {
        return size;
    }
}
