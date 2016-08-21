package org.thymeleafextras.sqldialect;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleafextras.sqldialect.command.ParamsCommand;

/**
 * Stores SQL parameters for use in QueryAttrProcessor.
 *
 * Usage:
 * 
 * <pre>
 * {@code
 *    <html sql:query="SELECT description, price FROM products WHERE id=?" sql:params="${param.id}">
 * }
 * </pre>
 */
public class ParamsAttrProcessor extends AbstractAttributeTagProcessor {

	public static final String ATTR_NAME = "params";
	// Before QueryAttrProcessor and UpdateAttrProcessor
	public static final int ATTR_PRECEDENCE = QueryAttrProcessor.ATTR_PRECEDENCE - 1;

	public ParamsAttrProcessor(final String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, ATTR_PRECEDENCE, true);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {
		new ParamsCommand(context, attributeValue).execute();
	}
}
