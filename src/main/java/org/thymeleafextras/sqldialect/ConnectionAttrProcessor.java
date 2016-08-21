package org.thymeleafextras.sqldialect;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleafextras.sqldialect.command.ConnectionCommand;

/**
 * Defines a JDBC connection to use in queries.
 *
 * Usage:
 * 
 * <pre>
 * {@code
 *    <html sql:connection=
"org.hsqldb.jdbcDriver, jdbc:hsqldb:mem:testdb, sa, ">
 * }
 * </pre>
 */
public class ConnectionAttrProcessor extends AbstractAttributeTagProcessor {

	public static final String ATTR_NAME = "connection";
	// Execute before QueryAttrProcessor, UpdateAttrProcessor and
	// ParamsAttrProcessor
	public static final int ATTR_PRECEDENCE = ParamsAttrProcessor.ATTR_PRECEDENCE - 1;

	public ConnectionAttrProcessor(final String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, ATTR_PRECEDENCE, true);

	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {
		new ConnectionCommand(context, attributeName, attributeValue).execute();
	}

}
