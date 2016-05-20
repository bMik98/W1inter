package com.concurrentminds.winter.reflection;

import java.util.List;

public interface Reflection {

    List<Class> getClassesFromPackage(String packageName);

    Reflection getClasses(String packageName);

    Reflection withAnnotation(Class annotation);

    Reflection and(Class annotation);

    List<Class> get();


}
