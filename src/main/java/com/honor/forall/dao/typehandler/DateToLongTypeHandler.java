package com.honor.forall.dao.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

@MappedJdbcTypes(JdbcType.BIGINT)
public class DateToLongTypeHandler extends BaseTypeHandler<Date> {

    @Override
    public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return format(value);
    }

    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return format(value);
    }

    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return format(value);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int columnIndex, Date date, JdbcType jdbcType) throws SQLException {
        Long time = date == null ? null : date.getTime();
        ps.setObject(columnIndex, time, Types.BIGINT);
    }

    private Date format(String time) {
        if (time == null) {
            return null;
        }
        return new Date(Long.valueOf(time));
    }

}
