package com.concurrentminds.winter.test;


import com.concurrentminds.winter.annotations.Report;
import com.concurrentminds.winter.annotations.Snowflake;

@Snowflake("Fast")
@Report("d:/reports/FlakeHare.txt")
public class FlakeHare {
}
