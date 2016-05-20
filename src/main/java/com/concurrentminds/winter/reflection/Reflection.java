package com.concurrentminds.winter.reflection;

import java.util.List;

public interface Reflection {

    List<Class> getClassesFromPackage(String packageName);

    Reflection getClasses(String packageName);

    List<Class> withAnnotation(Class annotation);


}
