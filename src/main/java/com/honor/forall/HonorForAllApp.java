package com.honor.forall;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.honor.forall.core.security.HonorOAuthAuthenticator;
import com.honor.forall.core.security.HonorAuthorizer;
import com.honor.forall.dao.HeroDao;
import com.honor.forall.dao.impl.HeroDaoImpl;
import com.honor.forall.dao.impl.UserDaoImpl;
import com.honor.forall.dao.mapper.HeroMapper;
import com.honor.forall.dao.mapper.UserMapper;
import com.honor.forall.dao.typehandler.DateToLongTypeHandler;
import com.honor.forall.dao.typehandler.EnumTypeHandler;
import com.honor.forall.dao.typehandler.HeroStatsTypeHandler;
import com.honor.forall.dao.typehandler.SpellDetailTypeHandler;
import com.honor.forall.dao.typehandler.UuidTypeHandler;
import com.honor.forall.exception.mapper.HonorAppExceptionMapper;
import com.honor.forall.exception.mapper.UnhandledExceptionMapper;
import com.honor.forall.health.DbHealthCheck;
import com.honor.forall.health.HonorForAllHealthCheck;
import com.honor.forall.model.base.AuthToken;
import com.honor.forall.model.base.User;
import com.honor.forall.model.db.AuthTokenDb;
import com.honor.forall.model.vm.HeroVm;
import com.honor.forall.model.vm.SpellVm;
import com.honor.forall.resources.HeroResource;
import com.honor.forall.resources.IndexResource;
import com.honor.forall.resources.LoginResource;
import com.honor.forall.service.HeroService;
import com.honor.forall.service.impl.HeroServiceImpl;
import com.honor.forall.service.impl.UserServiceImpl;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class HonorForAllApp extends Application<HonorForAllConfiguration> {

    private HonorForAllConfiguration config;
    private Environment environment;

    // dao
    private SqlSessionFactory sessionFactory;
    private HeroDao heroDao;
    private UserDaoImpl userDao;

    // services
    private HeroService heroService;
    private UserServiceImpl userService;

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
        setUpExceptionHandling();
        setUpAuditLogging();
        setupSecurityProvider();
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
        registerTypeHandlers();
        registerMappers();
    }

    private void registerAliases() {
        TypeAliasRegistry registry = sessionFactory.getConfiguration().getTypeAliasRegistry();
        registry.registerAlias(EnumTypeHandler.class);
        registry.registerAlias(UuidTypeHandler.class);
        registry.registerAlias(DateToLongTypeHandler.class);
        registry.registerAlias(HeroStatsTypeHandler.class);
        registry.registerAlias(SpellDetailTypeHandler.class);
        registry.registerAlias(HeroVm.class);
        registry.registerAlias(SpellVm.class);
        registry.registerAlias(AuthTokenDb.class);
        registry.registerAlias(User.class);
    }

    private void registerTypeHandlers() {
        TypeHandlerRegistry registry = sessionFactory.getConfiguration().getTypeHandlerRegistry();
        registry.register(UUID.class, UuidTypeHandler.class);
        registry.register(Date.class, DateToLongTypeHandler.class);
    }

    private void registerMappers() {
        Configuration c = sessionFactory.getConfiguration();
        c.addMapper(HeroMapper.class);
        c.addMapper(UserMapper.class);
    }

    private void setUpDao() {
        heroDao = new HeroDaoImpl(sessionFactory);
        userDao = new UserDaoImpl(sessionFactory);
    }

    private void setUpServices() {
        heroService = new HeroServiceImpl(heroDao);
        userService = new UserServiceImpl(userDao);
    }

    private void setUpResources() {
        environment.jersey().register(new IndexResource());
        environment.jersey().register(new HeroResource(heroService));
        environment.jersey().register(new LoginResource(userService));
    }

    private void setupSecurityProvider() {
        environment.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<User>()
                    .setAuthenticator(new HonorOAuthAuthenticator(userService))
                    .setAuthorizer(new HonorAuthorizer())
                    .setPrefix(AuthToken.Type.BEARER.toString())
                    .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

    private void setUpHealthChecks() {
        environment.healthChecks().register("mysql-db", new DbHealthCheck(sessionFactory));
        environment.healthChecks().register(getName(), new HonorForAllHealthCheck());
    }

    private void setUpExceptionHandling() {
        environment.jersey().register(new UnhandledExceptionMapper());
        environment.jersey().register(new HonorAppExceptionMapper());
    }

    private void setUpAuditLogging() {
        environment.jersey().register(new LoggingFilter(Logger.getLogger("OutboundRequestResponse"), true));
        environment.jersey().register(new LoggingFilter(Logger.getLogger("InboundRequestResponse"), true));
    }

}
