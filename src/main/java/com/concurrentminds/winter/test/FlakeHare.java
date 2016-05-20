package com.concurrentminds.winter.test;

import com.concurrentminds.winter.annotations.Report;
import com.concurrentminds.winter.annotations.Snowflake;

@Snowflake("Fast")
@Report("d:/reports/FlakeHare.txt")
public class FlakeHare implements FlakeAnimal {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public FlakeHare() {
        printPrivateNumber(100);
    }

    public FlakeHare(final String name) {
        this.name = name;
    }

    @Override
    public int number() {
        return 100;
    }

    @Override
    public String voice() {
        return "HareHare";
    }

    private void printPrivateNumber(final int number) {
        System.out.println(number);
    }
}
