package org.thymeleafextras.sqldialect.rowbean;

/**
 * Calculates the name of getters and setters.
 */
public class GetterSetterUtil {

    // FIXME: for booleans it should be "is" instead of "get"
    public static String getterNameFor(String fieldName) {
        StringBuilder getter = new StringBuilder();
        getter.append("get");
        getter.append(capitalize(fieldName));
        return getter.toString();
    }

    public static String setterNameFor(String fieldName) {
        StringBuilder setter = new StringBuilder();
        setter.append("set");
        setter.append(capitalize(fieldName));
        return setter.toString();
    }

    private static String capitalize(String fieldName) {
        return Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }
}
