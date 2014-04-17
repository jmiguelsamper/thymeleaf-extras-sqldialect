package org.thymeleafextras.sqldialect.command;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.thymeleaf.Arguments;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.util.Validate;
import static org.thymeleafextras.sqldialect.util.ExpressionUtil.expressionValue;

/**
 * Creates a JdbcTemplate using the provided properties.
 */
public class ConnectionCommand {

    public static final String CONNECTION_ATTR_NAME = "org.thymeleaf.extra.sqldialect.connection_attribute";

    private final Arguments arguments;
    private final String driver;
    private final String url;
    private final String username;
    private final String password;

    public ConnectionCommand(Arguments arguments, String propertyString) {
        this.arguments = arguments;
        Validate.notEmpty(propertyString, "Invalid connection configuration");
        String[] properties = propertyString.split(",");
        Validate.isTrue(properties.length == 4, "Invalid connection configuration");
        driver   = evaluate(properties[0]);
        url      = evaluate(properties[1]);
        username = evaluate(properties[2]);
        password = evaluate(properties[3]);
    }

    private String evaluate(String value) {
        return expressionValue(arguments, value.trim()).toString();
    }

    public void execute() {
        IWebContext context = (IWebContext) arguments.getContext();
        context.getRequestAttributes().put(CONNECTION_ATTR_NAME, createJdbcTemplate());
    }

    private JdbcTemplate createJdbcTemplate() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
		return new JdbcTemplate(dataSource);
    }
}
