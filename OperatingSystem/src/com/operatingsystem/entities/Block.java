package com.operatingsystem.entities;

public class Block {
    private int start;
    private int finish;

    public Block(int start, int finish) {
        this.start = start;
        this.finish = finish;
    }

    public int getStart() {
        return start;
    }

    public int getFinish() {
        return finish;
    }
}
