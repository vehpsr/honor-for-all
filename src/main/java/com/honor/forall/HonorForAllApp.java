package com.honor.forall;

import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.honor.forall.health.DbHealthCheck;
import com.honor.forall.health.HonorForAllHealthCheck;
import com.honor.forall.resources.IndexResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.ManagedDataSource;

public class HonorForAllApp extends Application<HonorForAllConfiguration> {

    private HonorForAllConfiguration config;
    private Environment environment;
    private SqlSessionFactory sessionFactory;

    public static void main(final String[] args) throws Exception {
        String[] mainArgs;
        if (args != null && args.length > 0) {
            mainArgs = args;
        } else {
            mainArgs = new String[]{"server", "configs/dev.yaml"};
        }
        new HonorForAllApp().run(mainArgs);
    }

    @Override
    public String getName() {
        return "honor-for-all";
    }

    @Override
    public void initialize(final Bootstrap<HonorForAllConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<HonorForAllConfiguration>());
        bootstrap.addBundle(new AssetsBundle("/assets/css", "/css", null, "css"));
        bootstrap.addBundle(new AssetsBundle("/assets/js", "/js", null, "js"));
        //bootstrap.addBundle(new AssetsBundle("/assets/fonts", "/fonts", null, "fonts"));
    }

    @Override
    public void run(HonorForAllConfiguration config, Environment environment) {
        this.config = config;
        this.environment = environment;

        setUpDataBase();
        setUpResources();
        setUpHealthChecks();
    }

    private void setUpDataBase() {
        ManagedDataSource dataSource = config.getDataSourceFactory().build(environment.metrics(), "mysql-db");
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        org.apache.ibatis.mapping.Environment myBatisEnvironment =
                new org.apache.ibatis.mapping.Environment("mybatis", transactionFactory, dataSource);
        Configuration mybatisConfiguration = new Configuration(myBatisEnvironment);
        sessionFactory = new SqlSessionFactoryBuilder().build(mybatisConfiguration);
        environment.lifecycle().manage(dataSource);
    }

    private void setUpResources() {
        environment.jersey().register(new IndexResource());
    }

    private void setUpHealthChecks() {
        environment.healthChecks().register("mysql-db", new DbHealthCheck(sessionFactory));
        environment.healthChecks().register(getName(), new HonorForAllHealthCheck());
    }

}
