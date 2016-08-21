package org.thymeleafextras.sqldialect.util;

import org.thymeleaf.context.WebEngineContext;

/**
 * Evaluates Thymeleaf expressions easily allowing one extra syntax. For
 * convenience, we allow ${params.id} as a synonym of ${params.id[0]}
 */
public class ArrayExpressionUtil {

	/**
	 */
	public static Object valueOrFirstElement(WebEngineContext context, String parameter) {
		Object value = ExpressionUtil.expressionValue(context, parameter.trim());

		if (isArrayWithOneElement(value)) {
			return firstValueOfArray(value);
		} else {
			if (value != null) {
				return value.toString();
			}
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
