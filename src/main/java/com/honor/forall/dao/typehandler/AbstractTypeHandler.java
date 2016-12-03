package com.honor.forall.dao.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.function.Function;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.JdbcType;

@MappedJdbcTypes(JdbcType.VARCHAR)
public abstract class AbstractTypeHandler<T> extends BaseTypeHandler<T> {

    private final Function<String, T> format;
    private final T nullValue;

    protected AbstractTypeHandler(Function<String, T> format, T nullValue) {
        this.format = format;
        this.nullValue = nullValue;
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return format(value);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return format(value);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return format(value);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int columnIndex, T type, JdbcType jdbcType) throws SQLException {
        String defaultValue = nullValue == null ? null : nullValue.toString();
        ps.setObject(columnIndex, Objects.toString(type, defaultValue), Types.VARCHAR);
    }

    private T format(String value) {
        if (value == null) {
            return nullValue;
        }
        return format.apply(value);
    }
}
