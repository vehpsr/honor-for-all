package com.honor.forall;

import com.honor.forall.health.HonorForAllHealthCheck;
import com.honor.forall.resources.IndexResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.assets.AssetsBundle;

public class HonorForAllApp extends Application<HonorForAllConfiguration> {

    public static void main(final String[] args) throws Exception {
        String[] mainArgs;
        if (args != null && args.length > 0) {
            mainArgs = args;
        } else {
            mainArgs = new String[]{"server"};
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
    public void run(final HonorForAllConfiguration config, final Environment environment) {
        environment.jersey().register(new IndexResource());
        environment.healthChecks().register(getName(), new HonorForAllHealthCheck());
    }

}
