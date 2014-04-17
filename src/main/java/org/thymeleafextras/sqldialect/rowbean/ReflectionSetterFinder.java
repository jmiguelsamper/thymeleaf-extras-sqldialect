package org.thymeleafextras.sqldialect.rowbean;

import java.lang.reflect.Method;
import static org.thymeleafextras.sqldialect.rowbean.GetterSetterUtil.setterNameFor;

/**
 * Finds a setter for a field using reflection.
 */
public class ReflectionSetterFinder {

    private final Class targetClass;

    private ReflectionSetterFinder(Class targetClass) {
        this.targetClass = targetClass;
    }

    public static ReflectionSetterFinder forClass(Class targetClass) {
        return new ReflectionSetterFinder(targetClass);
    }

    public Method setterLike(String fieldName, Class fieldClass) {
        String setterName = setterNameFor(fieldName);
        try {
            return targetClass.getMethod(setterName, fieldClass);
        } catch (NoSuchMethodException ex) {
            throw new IllegalArgumentException("No matching field found", ex);
        }
    }
}
