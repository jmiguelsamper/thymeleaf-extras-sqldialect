package org.thymeleafextras.config;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.dialect.IDialect;
import org.thymeleafextras.sqldialect.SqlDialect;
import org.thymeleaf.spring4.dialect.SpringStandardDialect;

/**
 * A simple servlet that spins up a Thymeleaf template engine, and uses it to
 * serve Thymeleaf templates as requested by the URL.
 *
 * Derived from https://github.com/ultraq/thymeleaf-template-servlet
 */
@WebServlet(
	name = "StandaloneThymeleafServlet",
	initParams = {
		@WebInitParam(
			name = StandaloneThymeleafServlet.INIT_PARAM_PREFIX,
			value = ""),
		@WebInitParam(
			name = StandaloneThymeleafServlet.INIT_PARAM_SUFFIX,
			value = ""),
		@WebInitParam(
			name = StandaloneThymeleafServlet.INIT_PARAM_TEMPLATEMODE,
			value = "HTML5")
	},
	urlPatterns = {
		"/", "*.html"
	}
)
public class StandaloneThymeleafServlet extends HttpServlet {

	public static final String DEFAULT_PAGE            = "index.html";
	public static final String INIT_PARAM_PREFIX       = "prefix";
	public static final String INIT_PARAM_SUFFIX       = "suffix";
	public static final String INIT_PARAM_TEMPLATEMODE = "templateMode";
	public static final String CHARACTER_ENCODING      = "UTF-8";

	private TemplateEngine templateengine;

	/**
	 * Initialize the Thymeleaf template engine.
	 */
	@Override
	public void init() {
		ServletConfig config = this.getServletConfig();

		ServletContextTemplateResolver templateresolver = new ServletContextTemplateResolver();
		templateresolver.setPrefix(config.getInitParameter(INIT_PARAM_PREFIX));
		templateresolver.setSuffix(config.getInitParameter(INIT_PARAM_SUFFIX));
		templateresolver.setTemplateMode(config.getInitParameter(INIT_PARAM_TEMPLATEMODE));
		templateresolver.setCacheable(false);
		templateresolver.setCharacterEncoding(CHARACTER_ENCODING);

		templateengine = new TemplateEngine();
		templateengine.setTemplateResolver(templateresolver);
        Set<IDialect> dialects = new HashSet<IDialect>();
		dialects.add(new SpringStandardDialect());
		dialects.add(new SqlDialect());
        templateengine.setDialects(dialects);
        templateengine.initialize();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
        processRequest(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
        processRequest(request, response);

	}

	/**
	 * Find and process a thymeleaf template with the name that matches the URL.
	 */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		String templatepath = request.getRequestURI().substring(request.getContextPath().length());
        // Maps / to index.html
        // FIXME: use welcome-file-list in web.xml
        if (templatepath.equals("/")) {
            templatepath += DEFAULT_PAGE;
        }
        response.setCharacterEncoding(CHARACTER_ENCODING);
		templateengine.process(templatepath,
				new WebContext(request, response, getServletContext()),
				response.getWriter());
    }
}
