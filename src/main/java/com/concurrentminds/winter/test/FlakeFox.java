package com.concurrentminds.winter.test;

import com.concurrentminds.winter.annotations.Denied;
import com.concurrentminds.winter.annotations.Snowflake;

@Snowflake("Sly")
@Denied
public class FlakeFox implements FlakeAnimal {
    private int number = 200;

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
        return "WathWath";
    }
}
