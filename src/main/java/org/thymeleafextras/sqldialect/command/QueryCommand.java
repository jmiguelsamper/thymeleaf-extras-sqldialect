package org.thymeleafextras.sqldialect.command;

import org.thymeleafextras.sqldialect.util.QueryMetadataExtractor;
import org.thymeleafextras.sqldialect.rowbean.FieldInfo;
import org.thymeleafextras.sqldialect.rowbean.RowPopulator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thymeleaf.Arguments;
import org.thymeleaf.util.Validate;
import static org.thymeleafextras.sqldialect.command.ConnectionCommand.CONNECTION_ATTR_NAME;
import static org.thymeleafextras.sqldialect.command.ParamsCommand.PARAMS_ATTR_NAME;
import org.thymeleafextras.sqldialect.rowbean.RowObjectMapper;

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

    public QueryCommand(Arguments arguments, String query) {
        Validate.notEmpty(query, "SQL query was not supplied");
        this.query = query;
        this.jdbcTemplate = (JdbcTemplate) arguments.getContext().getVariables().get(CONNECTION_ATTR_NAME);
        this.params = (Object[]) arguments.getLocalVariable(PARAMS_ATTR_NAME);
        this.metadataExtractor = new QueryMetadataExtractor(jdbcTemplate);
    }

    public Map<String, Object> execute() {
        Map<String, Object> variables = new HashMap<String, Object>();
        List<Object> rows = executeQuery();
        variables.put(RESULT_VARIABLE, rows);
        if (rows.size() == 1) {
            variables.put(SINGLE_RESULT_VARIABLE, rows.get(0));
        }
        return variables;
    }

    private List<Object> executeQuery() {
        FieldInfo[] fields = metadataExtractor.metadata(query);
        RowPopulator rowPopulator = new RowPopulator(fields);
        return jdbcTemplate.query(query, new RowObjectMapper(rowPopulator), params);
    }
}
