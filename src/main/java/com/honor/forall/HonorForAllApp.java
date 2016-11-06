package com.honor.forall;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;

import com.honor.forall.dao.HeroDao;
import com.honor.forall.dao.impl.HeroDaoImpl;
import com.honor.forall.dao.mapper.HeroMapper;
import com.honor.forall.dao.typehandler.EnumTypeHandler;
import com.honor.forall.dao.typehandler.HeroStatsTypeHandler;
import com.honor.forall.dao.typehandler.SpellStatDetailTypeHandler;
import com.honor.forall.exception.mapper.UnhandledExceptionMapper;
import com.honor.forall.health.DbHealthCheck;
import com.honor.forall.health.HonorForAllHealthCheck;
import com.honor.forall.model.vm.HeroVm;
import com.honor.forall.model.vm.SpellVm;
import com.honor.forall.resources.HeroResource;
import com.honor.forall.resources.IndexResource;
import com.honor.forall.service.HeroService;
import com.honor.forall.service.impl.HeroServiceImpl;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class HonorForAllApp extends Application<HonorForAllConfiguration> {

    private HonorForAllConfiguration config;
    private Environment environment;
    private SqlSessionFactory sessionFactory;
    private HeroDao heroDao;
    private HeroService heroService;

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
        setUpDao();
        setUpServices();
        setUpResources();
        setUpHealthChecks();
        setupExceptionHandling();
    }

    private void setUpDataBase() {
        ManagedDataSource dataSource = config.getDataSourceFactory().build(environment.metrics(), "mysql-db");
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        org.apache.ibatis.mapping.Environment myBatisEnvironment =
                new org.apache.ibatis.mapping.Environment("mybatis", transactionFactory, dataSource);
        Configuration mybatisConfiguration = new Configuration(myBatisEnvironment);
        sessionFactory = new SqlSessionFactoryBuilder().build(mybatisConfiguration);
        environment.lifecycle().manage(dataSource);

        registerAliases();
        registerMappers();
    }

    private void registerAliases() {
        TypeAliasRegistry registry = sessionFactory.getConfiguration().getTypeAliasRegistry();
        registry.registerAlias(EnumTypeHandler.class);
        registry.registerAlias(HeroStatsTypeHandler.class);
        registry.registerAlias(SpellStatDetailTypeHandler.class);
        registry.registerAlias(HeroVm.class);
        registry.registerAlias(SpellVm.class);
    }

    private void registerMappers() {
        Configuration c = sessionFactory.getConfiguration();
        c.addMapper(HeroMapper.class);
    }

    private void setUpDao() {
        heroDao = new HeroDaoImpl(sessionFactory);
    }

    private void setUpServices() {
        heroService = new HeroServiceImpl(heroDao);
    }

    private void setUpResources() {
        environment.jersey().register(new IndexResource());
        environment.jersey().register(new HeroResource(heroService));
    }

    private void setUpHealthChecks() {
        environment.healthChecks().register("mysql-db", new DbHealthCheck(sessionFactory));
        environment.healthChecks().register(getName(), new HonorForAllHealthCheck());
    }

    private void setupExceptionHandling() {
        environment.jersey().register(new UnhandledExceptionMapper());
    }

}
