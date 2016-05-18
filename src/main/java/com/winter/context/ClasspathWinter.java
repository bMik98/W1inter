package com.winter.context;

import java.util.List;

public class ClasspathWinter implements Winter {

    private List<Class> classes;

    public ClasspathWinter() {
    }

    public ClasspathWinter(String packageName) {
        addSnowflakes(packageName);
    }

    @Override
    public void addSnowflakes(String packageName) {

    }

    @Override
    public Object getSnowflakes(String beanName) {
        return null;
    }
}
