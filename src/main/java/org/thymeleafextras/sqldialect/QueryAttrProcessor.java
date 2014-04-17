package org.thymeleafextras.sqldialect;

import org.thymeleafextras.sqldialect.command.QueryCommand;
import java.util.Map;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleafextras.sqldialect.util.ExpressionUtil;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

/**
 * Executes an SQL query and stores the result in a variable called 'rows' (or 'row' if it is a single one).
 *
 * Usage:
 * <pre>
 * {@code
 *    <html sql:query="SELECT id, description, price FROM products">
 * }
 * </pre>
 */
public class QueryAttrProcessor extends AbstractAttrProcessor {

    public static final String ATTR_NAME = "query";
    public static final int PRECEDENCE = 1000;

    public QueryAttrProcessor() {
        super(ATTR_NAME);
    }

    @Override
    public int getPrecedence() {
        return PRECEDENCE;
    }

    @Override
    public ProcessorResult processAttribute(Arguments arguments, Element element, String attributeName) {
        String value = element.getAttributeValue(attributeName);
        String query = ExpressionUtil.expressionValue(arguments, value).toString();
        element.removeAttribute(attributeName);
        Map<String, Object> variables = new QueryCommand(arguments, query).execute();
        return ProcessorResult.setLocalVariables(variables);
    }
}
