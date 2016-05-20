package com.concurrentminds.winter.test;

import com.concurrentminds.winter.annotations.Copied;
import com.concurrentminds.winter.annotations.Snowflake;

@Snowflake("Brave")
@Copied
public class FlakeBear implements FlakeAnimal {
    private int number = 100;

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
        return "Beeeeeaar";
    }
}
