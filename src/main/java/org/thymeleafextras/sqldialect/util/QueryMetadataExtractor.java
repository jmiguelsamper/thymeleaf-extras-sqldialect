package org.thymeleafextras.sqldialect.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.thymeleafextras.sqldialect.rowbean.FieldInfo;

/**
 * Calculates the column names and types of the result of an SQL expression.
 */
public class QueryMetadataExtractor {

    private final JdbcTemplate jdbcTemplate;

    public QueryMetadataExtractor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public FieldInfo[] metadata(String query) {
        try {
            Object[] params = new Object[0];
            PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreatorFactory(query)
                .newPreparedStatementCreator(params);
            PreparedStatement ps = preparedStatementCreator
                .createPreparedStatement(jdbcTemplate.getDataSource().getConnection());
            ResultSetMetaData metadata = ps.getMetaData();
            FieldInfo[] fields = new FieldInfo[metadata.getColumnCount()];
            for (int i = 0; i < fields.length; i++) {
                int sqlColumnIndex = i + 1;
                int columnTypeIndex = metadata.getColumnType(sqlColumnIndex);
                Class<?> columnType = convert(columnTypeIndex);
                fields[i] = new FieldInfo(metadata.getColumnLabel(sqlColumnIndex), columnType);
            }
            ps.close();
            return fields;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("The columns of the query couldn't be parsed", ex);
        }
    }

    private Class<?> convert(int sqlType) {
        switch (sqlType) {
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
                return String.class;
            case Types.NUMERIC:
            case Types.DECIMAL:
                return BigDecimal.class;
            case Types.BIT:
                return Boolean.class;
            case Types.TINYINT:
                return Byte.class;
            case Types.SMALLINT:
                return Short.class;
            case Types.INTEGER:
                return Integer.class;
            case Types.BIGINT:
                return Long.class;
            case Types.REAL:
            case Types.FLOAT:
            case Types.DOUBLE:
                return Double.class;
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
                return Byte[].class;
            case Types.DATE:
                return Date.class;
            case Types.TIME:
                return Time.class;
            case Types.TIMESTAMP:
                return Timestamp.class;
            default : return Object.class;
        }
    }
}
