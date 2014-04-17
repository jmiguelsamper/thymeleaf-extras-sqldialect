package org.thymeleafextras.sqldialect.command;

import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thymeleaf.Arguments;
import org.thymeleaf.util.Validate;
import static org.thymeleafextras.sqldialect.command.ConnectionCommand.CONNECTION_ATTR_NAME;
import static org.thymeleafextras.sqldialect.command.ParamsCommand.PARAMS_ATTR_NAME;

/**
 * Executes a SQL update statement.
 */
public class UpdateCommand {

    private static final String RESULT_VARIABLE = "result";

    private final String query;
    private final JdbcTemplate jdbcTemplate;
    private final Object[] params;

    public UpdateCommand(Arguments arguments, String query) {
        Validate.notEmpty(query, "SQL statement was not supplied");
        this.query = query;
        this.jdbcTemplate = (JdbcTemplate) arguments.getContext().getVariables().get(CONNECTION_ATTR_NAME);
        this.params = (Object[]) arguments.getLocalVariable(PARAMS_ATTR_NAME);
    }

    public Map<String, Object> execute() {
        Map<String, Object> variables = new HashMap<String, Object>();
        int result = executeUpdate();
        variables.put(RESULT_VARIABLE, result);
        return variables;
    }

    private int executeUpdate() {
        return jdbcTemplate.update(query, params);
    }
}
