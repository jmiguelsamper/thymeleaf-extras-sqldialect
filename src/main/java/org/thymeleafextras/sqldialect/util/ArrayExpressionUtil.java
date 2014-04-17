package org.thymeleafextras.sqldialect.util;

import org.thymeleaf.Arguments;

/**
 * Evaluates Thymeleaf expressions easily allowing one extra syntax.
 * For convenience, we allow ${params.id} as a synonym of ${params.id[0]}
 */
public class ArrayExpressionUtil {

    /**
     */
    public static Object valueOrFirstElement(Arguments arguments, String parameter) {
        Object value = ExpressionUtil.expressionValue(arguments, parameter.trim());
        if (isArrayWithOneElement(value)) {
            return firstValueOfArray(value);
        } else {
            return value;
        }
    }

    private static boolean isArrayWithOneElement(Object value) {
        return value.getClass().isArray() && hasOneElement(value);
    }

    private static boolean hasOneElement(Object value) {
        return ((Object[]) value).length == 1;
    }

    private static Object firstValueOfArray(Object value) {
        return ((Object[]) value)[0];
    }
}
