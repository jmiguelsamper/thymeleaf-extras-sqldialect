package org.thymeleafextras.sqldialect.command;

import static org.thymeleafextras.sqldialect.command.ConnectionCommand.CONNECTION_ATTR_NAME;
import static org.thymeleafextras.sqldialect.command.ParamsCommand.PARAMS_ATTR_NAME;

import org.springframework.jdbc.core.JdbcTemplate;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.WebEngineContext;
import org.thymeleaf.util.Validate;

/**
 * Executes a SQL update statement.
 */
public class UpdateCommand {

	private static final String RESULT_VARIABLE = "result";

	private final String query;
	private final JdbcTemplate jdbcTemplate;
	private final Object[] params;
	private final WebEngineContext context;

	public UpdateCommand(ITemplateContext ctx, String value) {
		Validate.notEmpty(value, "SQL statement was not supplied");
		context = (WebEngineContext) ctx;
		query = value;
		jdbcTemplate = (JdbcTemplate) ctx.getVariable(CONNECTION_ATTR_NAME);
		params = (Object[]) ctx.getVariable(PARAMS_ATTR_NAME);
	}

	public void execute() {
		int result = executeUpdate();
		context.setVariable(RESULT_VARIABLE, result);
	}

	private int executeUpdate() {
		return jdbcTemplate.update(query, params);
	}
}
