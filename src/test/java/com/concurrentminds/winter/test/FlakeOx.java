package com.concurrentminds.winter.test;

import com.concurrentminds.winter.annotations.Snowflake;

@Snowflake("Strong")
public class FlakeOx implements FlakeAnimal {
    private int number = 400;

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String voice() {
        return "Moooo";
    }
}
