package org.thymeleafextras.sqldialect.rowbean;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * Spring JDBCTemplate's RowMapper for a RowBean.
 */
public class RowObjectMapper implements RowMapper<Object> {

    private final RowPopulator rowPopulator;

    public RowObjectMapper(RowPopulator rowPopulator) {
        this.rowPopulator = rowPopulator;
    }

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Object[] values = new Object[rowPopulator.numberOfFields()];
        for (int i = 0; i < values.length; i++) {
            FieldInfo fieldInfo = rowPopulator.get(i);
            values[i] = rs.getObject(fieldInfo.getFieldName(), fieldInfo.getFieldType());
        }
        return rowPopulator.buildRowBean(values);
    }

}
