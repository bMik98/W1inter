package com.concurrentminds.winter;

import com.concurrentminds.winter.annotations.Snowflake;
import com.concurrentminds.winter.exceptions.SnowflakeNameDuplicationException;
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
        try {
            addSnowflakes(packageName);
        } catch (SnowflakeNameDuplicationException e) {
            e.printStackTrace();
        }
    }

    public void addSnowflakes(String packageName) throws SnowflakeNameDuplicationException {
        classes.clear();
        List<Class<?>> classList = new ContextUtil().getClassesFromPackage(packageName);
        for (Class item : classList) {
            Snowflake snowflake = (Snowflake) item.getAnnotation(Snowflake.class);
            if (snowflake != null) {
                if (!classes.containsKey(snowflake.value())) {
                    classes.put(snowflake.value(), item);
                    System.out.println(snowflake.value() + " " + item.getSimpleName());
                } else {
                    throw new SnowflakeNameDuplicationException(snowflake.value());
                }
            }
        }
    }

    public Object getSnowflake(String snowflakeName) {
        return null;
    }
}
