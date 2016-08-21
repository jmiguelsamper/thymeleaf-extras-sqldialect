package org.thymeleafextras.sqldialect.command;

import static org.thymeleafextras.sqldialect.util.ExpressionUtil.expressionValue;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.WebEngineContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.util.Validate;

/**
 * Creates a JdbcTemplate using the provided properties.
 */
public class ConnectionCommand {

	public static final String CONNECTION_ATTR_NAME = "org.thymeleaf.extra.sqldialect.connection_attribute";

	private final String driver;
	private final String url;
	private final String username;
	private final String password;
	private final WebEngineContext context;

	public ConnectionCommand(ITemplateContext ctx, AttributeName attributeName, String attributeValue) {
		Validate.notEmpty(attributeValue, "Invalid connection configuration");
		String[] properties = attributeValue.split(",");
		Validate.isTrue(properties.length == 4, "Invalid connection configuration");
		driver = evaluate(properties[0]);
		url = evaluate(properties[1]);
		username = evaluate(properties[2]);
		password = evaluate(properties[3]);
		context = (WebEngineContext) ctx;
	}

	private String evaluate(String value) {
		return expressionValue(context, value.trim()).toString();
	}

	public void execute() {
		context.getRequest().setAttribute(CONNECTION_ATTR_NAME, createJdbcTemplate());
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
