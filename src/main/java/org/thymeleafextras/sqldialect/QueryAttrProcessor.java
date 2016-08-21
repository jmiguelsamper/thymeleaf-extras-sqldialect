package org.thymeleafextras.sqldialect;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleafextras.sqldialect.command.QueryCommand;

/**
 * Executes an SQL query and stores the result in a variable called 'rows' (or
 * 'row' if it is a single one).
 *
 * Usage:
 * 
 * <pre>
 * {@code
 *    <html sql:query="SELECT id, description, price FROM products">
 * }
 * </pre>
 */
public class QueryAttrProcessor extends AbstractAttributeTagProcessor {

	public static final String ATTR_NAME = "query";
	public static final int ATTR_PRECEDENCE = 1000;

	public QueryAttrProcessor(final String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, ATTR_PRECEDENCE, true);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {
		new QueryCommand(context, attributeValue).execute();
	}
}
