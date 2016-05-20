package com.concurrentminds.winter.services;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ReportGeneratorServiceImplTest {

    @Test
    public void removeSlashFromPath() throws Exception {
        //input params
        String path = "C:\\fasfs\\asdasd\\";
        //class to test
        ReportGeneratorServiceImpl service = new ReportGeneratorServiceImpl();
        String result = service.removeLastSlash(path);
        //asserts
        String expected = "C:\\fasfs\\asdasd";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void dontremoveSlashFromPath() throws Exception {
        //input params
        String path = "C:\\fasfs\\asdasd";
        //class to test
        ReportGeneratorServiceImpl service = new ReportGeneratorServiceImpl();
        String result = service.removeLastSlash(path);
        //asserts
        Assert.assertEquals(path, result);
    }


}

