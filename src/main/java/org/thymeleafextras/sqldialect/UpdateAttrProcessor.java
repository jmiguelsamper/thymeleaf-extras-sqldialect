package org.thymeleafextras.sqldialect;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleafextras.sqldialect.command.UpdateCommand;

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
public class UpdateAttrProcessor extends AbstractAttributeTagProcessor {

    public static final String ATTR_NAME = "update";
    public static final int ATTR_PRECEDENCE = QueryAttrProcessor.ATTR_PRECEDENCE;

    public UpdateAttrProcessor(final String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, ATTR_PRECEDENCE, true);
    }

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {
		new UpdateCommand(context, attributeValue).execute();
	}
}
