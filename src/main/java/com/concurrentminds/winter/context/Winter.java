package com.concurrentminds.winter.context;

import com.concurrentminds.winter.annotations.Copied;
import com.concurrentminds.winter.annotations.Denied;
import com.concurrentminds.winter.annotations.Report;
import com.concurrentminds.winter.annotations.Snowflake;
import com.concurrentminds.winter.exceptions.SnowflakeDeniedException;
import com.concurrentminds.winter.exceptions.SnowflakeDoesNotExistException;
import com.concurrentminds.winter.exceptions.SnowflakeNameDuplicationException;

import com.concurrentminds.winter.reflection.Reflection;
import com.concurrentminds.winter.reflection.ReflectionImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Winter {

    private final static Logger logger = LogManager.getLogger(Winter.class);

    private final Reflection reflection;
    private Map<String, Class<?>> classes;
    private Map<String, Object> instances;

    public Winter() {
        this.reflection = new ReflectionImpl();
        classes = new HashMap<>();
        instances = new HashMap<>();
    }

    public Winter(String packageName) {
        this();
        try {
            addSnowflakes(packageName);
        } catch (Exception e) {
            logger.error("Exception in loading classes from package", e);
        }
    }

    public void addSnowflakes(String packageName)
            throws SnowflakeNameDuplicationException, IllegalAccessException, InstantiationException {
        classes.clear();
        instances.clear();

        List<Class> classList = reflection.getClasses(packageName)
                .withAnnotation(Snowflake.class)
                .get();

        for (Class item : classList) {
            Snowflake snowflake = (Snowflake) item.getAnnotation(Snowflake.class);
                if (!classes.containsKey(snowflake.value())) {
                    classes.put(snowflake.value(), item);
                    logger.debug(snowflake.value() + " " + item.getSimpleName());
                    instances.put(snowflake.value(), item.newInstance());
                } else {
                    throw new SnowflakeNameDuplicationException(snowflake.value());
                }
        }

        List<Class> reportClasses = reflection.getClasses(packageName)
                .withAnnotation(Snowflake.class)
                .and(Report.class)
                .get();

    }

    public Object getSnowflake(String snowflakeName)
            throws SnowflakeDoesNotExistException, SnowflakeDeniedException, IllegalAccessException, InstantiationException {
        if (!classes.containsKey(snowflakeName)) {
            throw new SnowflakeDoesNotExistException(snowflakeName);
        }
        Class cl = classes.get(snowflakeName);
        if (cl.isAnnotationPresent(Denied.class)) {
            throw new SnowflakeDeniedException(snowflakeName);
        }
        if (cl.isAnnotationPresent(Copied.class)) {
            return cl.newInstance();
        }
        return instances.get(snowflakeName);
    }
}
