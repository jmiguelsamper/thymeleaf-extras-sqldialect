package org.thymeleafextras.sqldialect.command;

import static org.thymeleafextras.sqldialect.util.ArrayExpressionUtil.valueOrFirstElement;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.WebEngineContext;
import org.thymeleaf.util.Validate;

/**
 * Parses SQL parameters.
 */
public class ParamsCommand {

	public static final String PARAMS_ATTR_NAME = "org.thymeleaf.extra.sqldialect.params_attribute";

	private final String[] parameters;
	private final Object[] processedParameters;
	private final WebEngineContext context;

	public ParamsCommand(ITemplateContext ctx, String parameterString) {
		Validate.notEmpty(parameterString, "Parameters were not supplied");
		parameters = parameterString.split(",");
		processedParameters = new Object[parameters.length];
		context = (WebEngineContext) ctx;
	}

	public void execute() {
		for (int i = 0; i < parameters.length; i++) {
			processedParameters[i] = valueOrFirstElement(context, parameters[i]);
		}
		context.setVariable(PARAMS_ATTR_NAME, processedParameters);
	}
}
