package com.concurrentminds.winter.test;

import com.concurrentminds.winter.annotations.Copied;
import com.concurrentminds.winter.annotations.Snowflake;

@Snowflake("Brave")
@Copied
public class FlakeBear implements FlakeAnimal {
    @Override
    public int number() {
        return 300;
    }

    @Override
    public String voice() {
        return "Beeeeeaar";
    }
}
