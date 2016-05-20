package com.concurrentminds.winter.context;

import com.concurrentminds.winter.test.FlakeHare;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    }

    @Test
    public void testGatherSnowflakes() throws Exception {

    }

    @Test
    public void testReportSnowflakes() throws Exception {

    }

    @Test
    public void testGetSnowflake() throws Exception {

    }

    @Test
    public void testCreateInstance() throws Exception {
        FlakeHare hare = (FlakeHare) winter.createInstance(FlakeHare.class);
        assertNotNull(hare);
    }

    @Test
    public void testGetLastError() throws Exception {
        assertEquals("", winter.getLastError());
        String SNOWFLAKE = "123456";
        winter.getSnowflake(SNOWFLAKE);
        assertEquals(String.format(winter.ERROR_NOT_EXISTS, SNOWFLAKE), winter.getLastError());
    }
}