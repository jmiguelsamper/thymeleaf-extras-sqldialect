package org.thymeleafextras.sqldialect;

import java.util.HashSet;
import java.util.Set;
import org.thymeleaf.dialect.AbstractXHTMLEnabledDialect;
import org.thymeleaf.processor.IProcessor;

/**
 * Thymeleaf dialect that provides direct SQL execution.
 */
public class SqlDialect extends AbstractXHTMLEnabledDialect {

    public static final String ATTR_PREFIX = "sql";

    @Override
    public String getPrefix() {
        return ATTR_PREFIX;
    }

    @Override
    public Set<IProcessor> getProcessors() {
        Set<IProcessor> attrProcessors = new HashSet<IProcessor>();
        attrProcessors.add(new ConnectionAttrProcessor());
        attrProcessors.add(new ParamsAttrProcessor());
        attrProcessors.add(new QueryAttrProcessor());
        attrProcessors.add(new UpdateAttrProcessor());
        return attrProcessors;
    }
}
