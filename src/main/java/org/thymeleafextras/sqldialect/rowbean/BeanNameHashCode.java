package org.thymeleafextras.sqldialect.rowbean;

import java.util.List;

/**
 * Creates an unique hash code to identify a set of fields.
 */
public class BeanNameHashCode {

    public static int hashCodeFor(List<FieldInfo> fields) {
        StringBuilder sb = new StringBuilder();
        for (FieldInfo fieldInfo : fields) {
            sb.append(fieldInfo.getFieldName());
            sb.append(fieldInfo.getFieldType().getCanonicalName());
        }
        return sb.toString().hashCode();
    }
}
