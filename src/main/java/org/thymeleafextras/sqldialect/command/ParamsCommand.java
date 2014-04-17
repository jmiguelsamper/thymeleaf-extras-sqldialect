package org.thymeleafextras.sqldialect.command;

import java.util.HashMap;
import java.util.Map;
import org.thymeleaf.Arguments;
import org.thymeleaf.util.Validate;
import static org.thymeleafextras.sqldialect.util.ArrayExpressionUtil.valueOrFirstElement;

/**
 * Parses SQL parameters.
 */
public class ParamsCommand {

    public static final String PARAMS_ATTR_NAME = "org.thymeleaf.extra.sqldialect.params_attribute";

    private final Arguments arguments;
    private final String[] parameters;
    private final Object[] processedParameters;;

    public ParamsCommand(Arguments arguments, String parameterString) {
        this.arguments = arguments;
        Validate.notEmpty(parameterString, "Parameters were not supplied");
        this.parameters = parameterString.split(",");
        this.processedParameters = new Object[parameters.length];
    }

    public Map<String, Object> execute() {
        for (int i = 0; i < parameters.length; i++) {
            processedParameters[i] = valueOrFirstElement(arguments, parameters[i]);
        }
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(PARAMS_ATTR_NAME, processedParameters);
        return variables;
    }
}
