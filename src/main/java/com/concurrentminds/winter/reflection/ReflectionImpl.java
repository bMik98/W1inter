package com.concurrentminds.winter.reflection;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReflectionImpl implements Reflection {

    private static final char PKG_SEPARATOR = '.';
    private static final char DIR_SEPARATOR = '/';
    private static final String CLASS_FILE_SUFFIX = ".class";
    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

    private List<Class> classes = new ArrayList<>();

    @Override
    public List<Class> getClassesFromPackage(String packageName) {
        String scannedPath = packageName.replace(PKG_SEPARATOR, DIR_SEPARATOR);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, packageName));
        }
        File scannedDir = new File(scannedUrl.getFile());
        return Stream.of(scannedDir.listFiles())
                .flatMap(e -> find(e, packageName).stream())
                .collect(Collectors.toList());
    }

    @Override
    public Reflection getClasses(String packageName) {
        this.classes = getClassesFromPackage(packageName);
        return this;
    }

    @Override
    public Reflection withAnnotation(Class annotation) {
        this.classes = this.classes.stream()
                .filter(e -> e.getAnnotation(annotation) != null)
                .collect(Collectors.toList());
        return this;
    }

    @Override
    public Reflection and(Class annotation) {
        this.classes = withAnnotation(annotation).get();
        return this;
    }


    @Override
    public List<Class> get() {
        return this.classes;
    }

    private List<Class<?>> find(File file, String scannedPackage) {
        List<Class<?>> classes = new ArrayList<>();
        String resource = scannedPackage + PKG_SEPARATOR + file.getName();
        if (file.isDirectory()) {
            Stream.of(file.listFiles()).forEach(e -> find(e, resource));
        } else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
            int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }
}
