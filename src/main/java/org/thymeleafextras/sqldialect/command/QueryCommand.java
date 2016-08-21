package org.thymeleafextras.sqldialect.command;

import static org.thymeleafextras.sqldialect.command.ConnectionCommand.CONNECTION_ATTR_NAME;
import static org.thymeleafextras.sqldialect.command.ParamsCommand.PARAMS_ATTR_NAME;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.WebEngineContext;
import org.thymeleaf.util.Validate;
import org.thymeleafextras.sqldialect.rowbean.FieldInfo;
import org.thymeleafextras.sqldialect.rowbean.RowObjectMapper;
import org.thymeleafextras.sqldialect.rowbean.RowPopulator;
import org.thymeleafextras.sqldialect.util.QueryMetadataExtractor;

/**
 * Executes a SQL query.
 */
public class QueryCommand {

	private static final String RESULT_VARIABLE = "rows";
	private static final String SINGLE_RESULT_VARIABLE = "row";

	private final String query;
	private final JdbcTemplate jdbcTemplate;
	private final Object[] params;
	private final QueryMetadataExtractor metadataExtractor;
	private final WebEngineContext context;

	public QueryCommand(ITemplateContext ctx, String value) {
		Validate.notEmpty(value, "SQL query was not supplied");
		query = value;
		context = (WebEngineContext) ctx;
		jdbcTemplate = (JdbcTemplate) context.getRequest().getAttribute(CONNECTION_ATTR_NAME);
		params = (Object[]) context.getVariable(PARAMS_ATTR_NAME);
		metadataExtractor = new QueryMetadataExtractor(jdbcTemplate);
	}

	public void execute() {
		List<Object> rows = executeQuery();
		context.setVariable(RESULT_VARIABLE, rows);
		if (rows.size() == 1) {
			context.setVariable(SINGLE_RESULT_VARIABLE, rows.get(0));
		}
	}

	private List<Object> executeQuery() {
		FieldInfo[] fields = metadataExtractor.metadata(query);
		RowPopulator rowPopulator = new RowPopulator(fields);
		return jdbcTemplate.query(query, new RowObjectMapper(rowPopulator), params);
	}
}
