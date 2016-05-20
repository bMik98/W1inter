package com.concurrentminds.winter.context;

import com.concurrentminds.winter.test.FlakeBear;
import com.concurrentminds.winter.test.FlakeFox;
import com.concurrentminds.winter.test.FlakeOx;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WinterTest {
    Winter winter;

    @Before
    public void setUp() throws Exception {
        winter = new Winter();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAddSnowflakes() throws Exception {
        int size = winter.addSnowflakes("com.concurrentminds.winter.test");
        assertEquals(4, size);
    }

    @Test
    public void testGetSnowflakeSingle() throws Exception {
        winter.addSnowflakes("com.concurrentminds.winter.test");
        FlakeOx flakeOx = (FlakeOx) winter.getSnowflake("Strong");
        FlakeOx flakeOx2 = (FlakeOx) winter.getSnowflake("Strong");
        assertEquals(flakeOx.getNumber(), flakeOx2.getNumber());
        flakeOx.setNumber(2001);
        assertEquals(flakeOx.getNumber(), flakeOx2.getNumber());
        flakeOx2.setNumber(2002);
        assertEquals(flakeOx.getNumber(), flakeOx2.getNumber());
    }

    @Test
    public void testGetSnowflakeCopied() throws Exception {
        winter.addSnowflakes("com.concurrentminds.winter.test");
        FlakeBear flakeBear = (FlakeBear) winter.getSnowflake("Brave");
        FlakeBear flakeBear2 = (FlakeBear) winter.getSnowflake("Brave");
        assertEquals(flakeBear.getNumber(), flakeBear2.getNumber());
        flakeBear.setNumber(3001);
        assertNotEquals(flakeBear.getNumber(), flakeBear2.getNumber());
        flakeBear2.setNumber(3002);
        assertNotEquals(flakeBear.getNumber(), flakeBear2.getNumber());
    }

    @Test
    public void testGetSnowflakeDenied() throws Exception {
        winter.addSnowflakes("com.concurrentminds.winter.test");
        FlakeFox flakeFox = (FlakeFox) winter.getSnowflake("Sly");
        assertNull(flakeFox);
    }

    @Test
    public void testCreateInstance() throws Exception {
        FlakeBear animal = (FlakeBear) winter.createInstance(FlakeBear.class);
        assertNotNull(animal);
    }

    @Test
    public void testGetLastError() throws Exception {
        assertEquals("", winter.getLastError());
        String SNOWFLAKE = "123456";
        winter.getSnowflake(SNOWFLAKE);
        assertEquals(String.format(winter.ERROR_NOT_EXISTS, SNOWFLAKE), winter.getLastError());
    }
}