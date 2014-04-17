package org.thymeleafextras.sqldialect;

import org.thymeleafextras.sqldialect.command.UpdateCommand;
import java.util.Map;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleafextras.sqldialect.util.ExpressionUtil;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

/**
 * Executes an SQL update statement and returns the result (row count) in a variable 'result'.
 *
 * Usage:
 * <pre>
 * {@code
 *    <html sql:update="INSERT INTO products(description, price) VALUES('Pink table', 250)">
 * }
 * </pre>
 */
public class UpdateAttrProcessor extends AbstractAttrProcessor {

    public static final String ATTR_NAME = "update";
    public static final int PRECEDENCE = QueryAttrProcessor.PRECEDENCE;

    public UpdateAttrProcessor() {
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
        Map<String, Object> variables = new UpdateCommand(arguments, query).execute();
        return ProcessorResult.setLocalVariables(variables);
    }
}
