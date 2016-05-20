package com.concurrentminds.winter.context;

import com.concurrentminds.winter.annotations.Copied;
import com.concurrentminds.winter.annotations.Denied;
import com.concurrentminds.winter.annotations.Report;
import com.concurrentminds.winter.annotations.Snowflake;
import com.concurrentminds.winter.reflection.Reflection;
import com.concurrentminds.winter.reflection.ReflectionImpl;
import com.concurrentminds.winter.services.ReportGeneratorService;
import com.concurrentminds.winter.services.ReportGeneratorServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Winter {
    public static final String ERROR_DENIED = "Access to snowflake `%s` was denied";
    public static final String ERROR_NOT_EXISTS = "Snowflake `%s` does not exist";
    public static final String ERROR_DUPLICATION = "Snowflake `%s` already exists";
    private final static Logger logger = LogManager.getLogger(Winter.class);
    private final Reflection reflection;
    private final ReportGeneratorService reportService;
    private Map<String, Class> classes;
    private Map<String, Object> instances;
    private String lastError;

    public Winter() {
        this.reflection = new ReflectionImpl();
        this.reportService = new ReportGeneratorServiceImpl();
        this.classes = new HashMap<>();
        this.instances = new HashMap<>();
        lastError = "";
    }

    public Winter(String packageName) {
        this();
        addSnowflakes(packageName);
    }

    public void addSnowflakes(String packageName) {
        clean();
        List<Class> classList = reflection.getClasses(packageName)
                .withAnnotation(Snowflake.class)
                .get();
        gatherSnowflakes(classList);
        List<Class> reportClasses = reflection.getClasses(packageName)
                .withAnnotation(Snowflake.class)
                .and(Report.class)
                .get();
        reportSnowflakes(reportClasses);
    }

    public void clean() {
        classes.clear();
        instances.clear();
    }

    public void gatherSnowflakes(List<Class> classList) {
        for (Class item : classList) {
            Snowflake snowflake = (Snowflake) item.getAnnotation(Snowflake.class);
            if (!classes.containsKey(snowflake.value())) {
                classes.put(snowflake.value(), item);
                logger.debug(snowflake.value() + " " + item.getSimpleName());
                instances.put(snowflake.value(), createInstance(item));
            } else {
                lastError = String.format(ERROR_DUPLICATION, snowflake.value());
                logger.error(lastError);
                return;
            }
        }
    }

    public void reportSnowflakes(List<Class> reportClasses) {
        reportClasses.parallelStream().forEach(e -> {
            Report report = (Report) e.getAnnotation(Report.class);
            Snowflake bean = (Snowflake) e.getAnnotation(Snowflake.class);
            reportService.generateReport(report.value(), bean.value(), e);
        });
    }

    public Object getSnowflake(String snowflakeName) {
        if (!classes.containsKey(snowflakeName)) {
            lastError = String.format(ERROR_NOT_EXISTS, snowflakeName);
            logger.error(lastError);
            return null;
        }
        Class c = classes.get(snowflakeName);
        if (c.isAnnotationPresent(Denied.class)) {
            lastError = String.format(ERROR_DENIED, snowflakeName);
            logger.error(lastError);
            return null;
        }
        return (c.isAnnotationPresent(Copied.class)) ? createInstance(c) : instances.get(snowflakeName);
    }

    public Object createInstance(Class<?> c) {
        Object object;
        try {
            object = c.newInstance();
        } catch (Exception e) {
            lastError = e.getMessage();
            logger.error(lastError);
            return null;
        }
        return object;
    }

    public String getLastError() {
        return lastError;
    }
}
