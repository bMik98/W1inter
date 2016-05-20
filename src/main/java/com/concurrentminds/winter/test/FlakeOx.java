package com.concurrentminds.winter.test;

import com.concurrentminds.winter.annotations.Snowflake;

@Snowflake("Strong")
public class FlakeOx implements FlakeAnimal {
    @Override
    public int number() {
        return 400;
    }

    @Override
    public String voice() {
        return "Moooo";
    }
}
