package com.honor.forall.health;

import java.sql.CallableStatement;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.codahale.metrics.health.HealthCheck;

public class DbHealthCheck extends HealthCheck {

    private final SqlSessionFactory sessionFactory;

    public DbHealthCheck(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    protected Result check() throws Exception {
        try (SqlSession session = sessionFactory.openSession(); CallableStatement callableStatement = session.getConnection().prepareCall("select 1")) {
            callableStatement.execute();
        }
        return Result.healthy();
    }

}
