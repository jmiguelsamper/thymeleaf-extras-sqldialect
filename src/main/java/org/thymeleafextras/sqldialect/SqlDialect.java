package org.thymeleafextras.sqldialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.IProcessorDialect;
import org.thymeleaf.processor.IProcessor;

/**
 * Thymeleaf dialect that provides direct SQL execution.
 */
public class SqlDialect implements IProcessorDialect {

	public static final String ATTR_PREFIX = "sql";
	public static final String NAME = "SqlDialect";

	public String getPrefix() {
		return ATTR_PREFIX;
	}

	public int getDialectProcessorPrecedence() {
		return 0;
	}

	public Set<IProcessor> getProcessors(String dialectPrefix) {
		Set<IProcessor> attrProcessors = new HashSet<IProcessor>();
		attrProcessors.add(new ConnectionAttrProcessor(dialectPrefix));
		attrProcessors.add(new ParamsAttrProcessor(dialectPrefix));
		attrProcessors.add(new QueryAttrProcessor(dialectPrefix));
		attrProcessors.add(new UpdateAttrProcessor(dialectPrefix));
		return attrProcessors;
	}

	public String getName() {
		return NAME;
	}
}
