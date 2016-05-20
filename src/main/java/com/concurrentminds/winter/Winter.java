package com.concurrentminds.winter;

import com.concurrentminds.winter.annotations.Copied;
import com.concurrentminds.winter.annotations.Denied;
import com.concurrentminds.winter.annotations.Snowflake;
import com.concurrentminds.winter.exceptions.SnowflakeDeniedException;
import com.concurrentminds.winter.exceptions.SnowflakeDoesNotExistException;
import com.concurrentminds.winter.exceptions.SnowflakeNameDuplicationException;
import com.concurrentminds.winter.utils.ContextUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Winter {

    private final static Logger logger = LogManager.getLogger(Winter.class);

    private Map<String, Class<?>> classes;
    private Map<String, Object> instances;

    public Winter() {
        classes = new HashMap<>();
        instances = new HashMap<>();
    }

    public Winter(String packageName) {
        this();
        try {
            addSnowflakes(packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSnowflakes(String packageName)
            throws SnowflakeNameDuplicationException, IllegalAccessException, InstantiationException {
        classes.clear();
        instances.clear();
        List<Class<?>> classList = new ContextUtil().getClassesFromPackage(packageName);
        for (Class item : classList) {
            Snowflake snowflake = (Snowflake) item.getAnnotation(Snowflake.class);
            if (snowflake != null) {
                if (!classes.containsKey(snowflake.value())) {
                    classes.put(snowflake.value(), item);
                    logger.debug(snowflake.value() + " " + item.getSimpleName());
                    instances.put(snowflake.value(), item.newInstance());
                } else {
                    throw new SnowflakeNameDuplicationException(snowflake.value());
                }
            }
        }
    }

    public Object getSnowflake(String snowflakeName)
            throws SnowflakeDoesNotExistException, SnowflakeDeniedException, IllegalAccessException, InstantiationException {
        if (!classes.containsKey(snowflakeName)) {
            throw new SnowflakeDoesNotExistException(snowflakeName);
        }
        Class<?> cl = classes.get(snowflakeName);
        if (cl.isAnnotationPresent(Denied.class)) {
            throw new SnowflakeDeniedException(snowflakeName);
        }
        if (cl.isAnnotationPresent(Copied.class)) {
            return cl.newInstance();
        }
        return instances.get(snowflakeName);
    }
}
