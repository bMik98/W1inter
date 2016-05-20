package com.concurrentminds.winter.test;

import com.concurrentminds.winter.annotations.Denied;
import com.concurrentminds.winter.annotations.Snowflake;

@Snowflake("Sly")
@Denied
public class FlakeFox implements FlakeAnimal{
    @Override
    public int number() {
        return 200;
    }

    @Override
    public String voice() {
        return "WathWath";
    }
}
