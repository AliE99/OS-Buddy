package com.operatingsystem.utils;

import java.util.Random;

public class RandomValue {

    private int maximum = Config.MAX;
    private int minimum = Config.MIN;

    public int address() {
        return (int) ((Math.random() * (maximum - 2)) + 2);
    }

    public int size() {
        return (int) ((Math.random() * (maximum - minimum)) + minimum);
    }

    public Boolean bool() {
        Random random = new Random();
        return random.nextBoolean();
    }

    public int time() {
        return (int) ((Math.random() * (3)) + 0);
    }

    public int request() {
        return 1;
    }
}
