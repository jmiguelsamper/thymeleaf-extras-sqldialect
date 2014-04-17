package org.thymeleafextras.sqldialect.rowbean;

/**
 * Encapsulates a field info, to be used to store SQL ResultSet.
 */
public class FieldInfo {

    private final String fieldName;
    private final Class<?> fieldType;

    public FieldInfo(String fieldName, Class<?> fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }
}
