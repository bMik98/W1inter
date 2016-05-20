package com.concurrentminds.winter.test;

import com.concurrentminds.winter.annotations.Report;
import com.concurrentminds.winter.annotations.Snowflake;

@Snowflake("Fast")
@Report("C:\\project\\W1inter\\src\\main\\resources\\")
public class FlakeHare implements FlakeAnimal {
    private String name;
    private int number = 300;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public FlakeHare() {
    }

    public FlakeHare(final String name) {
        this.name = name;
    }

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
        return "HareHare";
    }
}
