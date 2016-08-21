package org.thymeleafextras.sqldialect.util;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.WebEngineContext;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

/**
 * Simplifies evaluation of Thymeleaf expressions.
 */
public class ExpressionUtil {

	/**
	 * If the expression is a Thymeleaf expression, evaluates it. If not, return
	 * the same expression.
	 */
	public static Object expressionValue(WebEngineContext context, String expression) {
		if (isLiteralExpression(expression) || isSimpleExpression(expression) || isUrlExpression(expression)
				|| isMessageExpression(expression)) {
			return evaluate(context, expression);
		} else {
			return expression;
		}
	}

	private static boolean isLiteralExpression(String expression) {
		return expression.startsWith("|") && expression.endsWith("|");
	}

	private static boolean isMessageExpression(String expression) {
		return expression.startsWith("#{") && expression.endsWith("}");
	}

	private static boolean isUrlExpression(String expression) {
		return expression.startsWith("@{") && expression.endsWith("}");
	}

	private static boolean isSimpleExpression(String expression) {
		return expression.startsWith("${") && expression.endsWith("}");
	}

	private static Object evaluate(WebEngineContext context, String expression) {
		IEngineConfiguration configuration = context.getConfiguration();
		IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);
		IStandardExpression standardExpression = expressionParser.parseExpression(context, expression);
		return standardExpression.execute(context);
	}
}
