package org.thymeleafextras.sqldialect.util;

import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

/**
 * Simplifies evaluation of Thymeleaf expressions.
 */
public class ExpressionUtil {

    /**
     * If the expression is a Thymeleaf expression, evaluates it. If not, return the same expression.
     */
    public static Object expressionValue(Arguments arguments, String expression) {
        if (isLiteralExpression(expression) || isSimpleExpression(expression) ||
            isUrlExpression(expression) || isMessageExpression(expression)) {
            return evaluate(arguments, expression);
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

    private static Object evaluate(Arguments arguments, String expression) {
        Configuration configuration = arguments.getConfiguration();
        IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);
        IStandardExpression standardExpression = expressionParser.parseExpression(configuration, arguments, expression);
        return standardExpression.execute(configuration, arguments);
    }
}
