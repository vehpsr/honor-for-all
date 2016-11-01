package com.honor.forall.dao.typehandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;

public class EnumTypeHandler<E extends Enum<E>> extends org.apache.ibatis.type.EnumTypeHandler<E> {

    public EnumTypeHandler(Class<E> type) {
        super(type);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        if (jdbcType == null) {
            ps.setString(i, parameter.toString());
        } else {
            ps.setObject(i, parameter.toString(), jdbcType.TYPE_CODE); // see r3589
        }
    }

}
