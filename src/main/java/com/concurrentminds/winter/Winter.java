package com.concurrentminds.winter;

import com.concurrentminds.winter.utils.ContextUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Winter {
    private Map<String, Class<?>> classes;

    public Winter() {
        classes = new HashMap<>();
    }

    public Winter(String packageName) {
        this();
        addSnowflakes(packageName);
    }

    public void addSnowflakes(String packageName) {
        classes.clear();
        List<Class<?>> classList = new ContextUtil().getClassesFromPackage(packageName);
        System.out.println(classList.size());
    }

    public Object getSnowflake(String snowflakeName) {
        return null;
    }
}
