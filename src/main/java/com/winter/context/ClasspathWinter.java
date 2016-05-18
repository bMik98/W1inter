package com.winter.context;

import com.winter.context.util.ContextUtil;

import java.util.ArrayList;
import java.util.List;

public class ClasspathWinter implements Winter {

    private List<Class<?>> classes = new ArrayList<>();
    private final ContextUtil util = new ContextUtil();

    public ClasspathWinter() {
    }

    public ClasspathWinter(String packageName) {
        addSnowflakes(packageName);
    }

    @Override
    public void addSnowflakes(String packageName) {
        this.classes = util.getClassesFromPackage(packageName);
    }

    @Override
    public Object getSnowflakes(String beanName) {
        return null;
    }


}
