package com.concurrentminds.winter.test;

import com.concurrentminds.winter.context.Winter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppTest {

    private final static Logger logger = LogManager.getLogger(AppTest.class);

    public static void main(String[] args) {
        new AppTest().run();
    }

    public void run() {
        Winter winter = new Winter("com.concurrentminds.winter.test");
        try {
            FlakeOx flakeOx = (FlakeOx) winter.getSnowflake("Strong");
            FlakeOx flakeOx2 = (FlakeOx) winter.getSnowflake("Strong");
            System.out.printf("flakeOx #: %d  flakeOx2 #: %d %n", flakeOx.getNumber(), flakeOx2.getNumber());
            flakeOx.setNumber(2001);
            System.out.printf("flakeOx #: %d  flakeOx2 #: %d %n", flakeOx.getNumber(), flakeOx2.getNumber());
            flakeOx2.setNumber(2002);
            System.out.printf("flakeOx #: %d  flakeOx2 #: %d %n", flakeOx.getNumber(), flakeOx2.getNumber());
            FlakeBear flakeBear = (FlakeBear) winter.getSnowflake("Brave");
            FlakeBear flakeBear2 = (FlakeBear) winter.getSnowflake("Brave");
            System.out.printf("flakeBear #: %d  flakeBear2 #: %d %n", flakeBear.getNumber(), flakeBear2.getNumber());
            flakeBear.setNumber(3001);
            System.out.printf("flakeBear #: %d  flakeBear2 #: %d %n", flakeBear.getNumber(), flakeBear2.getNumber());
            flakeBear2.setNumber(3002);
            System.out.printf("flakeBear #: %d  flakeBear2 #: %d %n", flakeBear.getNumber(), flakeBear2.getNumber());
            FlakeHare flakeHare = (FlakeHare) winter.getSnowflake("Fast");
            flakeHare.setName("little rabbit");
            System.out.println(flakeHare.getName());
            FlakeHare flakeHare2 = (FlakeHare) winter.getSnowflake("Fast");
            System.out.println(flakeHare2.getName());

            //FlakeFox flakeFox = (FlakeFox) winter.getSnowflake("Sly");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
