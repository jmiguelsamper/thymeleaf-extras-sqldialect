package org.thymeleafextras.sqldialect;

import org.thymeleafextras.sqldialect.command.ParamsCommand;
import java.util.Map;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

/**
 * Stores SQL parameters for use in QueryAttrProcessor.
 *
 * Usage:
 * <pre>
 * {@code
 *    <html sql:query="SELECT description, price FROM products WHERE id=?"
 *          sql:params="${param.id}">
 * }
 * </pre>
 */
public class ParamsAttrProcessor extends AbstractAttrProcessor {

    public static final String ATTR_NAME = "params";
    // Before QueryAttrProcessor and UpdateAttrProcessor
    public static final int PRECEDENCE = QueryAttrProcessor.PRECEDENCE - 1;

    public ParamsAttrProcessor() {
        super(ATTR_NAME);
    }

    @Override
    public int getPrecedence() {
        return PRECEDENCE;
    }

    @Override
    public ProcessorResult processAttribute(Arguments arguments, Element element, String attributeName) {
        String parameters = element.getAttributeValue(attributeName);
        element.removeAttribute(attributeName);
        Map<String, Object> variables = new ParamsCommand(arguments, parameters).execute();
        return ProcessorResult.setLocalVariables(variables);
    }
}
