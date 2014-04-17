package org.thymeleafextras.sqldialect;

import org.thymeleafextras.sqldialect.command.ConnectionCommand;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

/**
 * Defines a JDBC connection to use in queries.
 *
 * Usage:
 * <pre>
 * {@code
 *    <html sql:connection="org.hsqldb.jdbcDriver, jdbc:hsqldb:mem:testdb, sa, ">
 * }
 * </pre>
 */
public class ConnectionAttrProcessor extends AbstractAttrProcessor {

    public static final String ATTR_NAME = "connection";
    // Execute before QueryAttrProcessor, UpdateAttrProcessor and ParamsAttrProcessor
    public static final int PRECEDENCE = ParamsAttrProcessor.PRECEDENCE - 1;

    public ConnectionAttrProcessor() {
        super(ATTR_NAME);
    }

    @Override
    public int getPrecedence() {
        return PRECEDENCE;
    }

    @Override
    public ProcessorResult processAttribute(Arguments arguments, Element element, String attributeName) {
        String properties = element.getAttributeValue(attributeName);
        element.removeAttribute(attributeName);
        new ConnectionCommand(arguments, properties).execute();
        return ProcessorResult.OK;
    }
}
