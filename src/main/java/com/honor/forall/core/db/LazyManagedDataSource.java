package com.honor.forall.core.db;

import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;

import com.codahale.metrics.MetricRegistry;

import io.dropwizard.db.ManagedPooledDataSource;

/**
 * Lazy implementation that delays pool initialization until it's actually being used and not on application startup.
 * Does nothing on {@link io.dropwizard.lifecycle.Managed#start()} and delegate this call to {@link #createPool()}
 */
public class LazyManagedDataSource extends ManagedPooledDataSource {

    public LazyManagedDataSource(PoolConfiguration config, MetricRegistry metricRegistry) {
        super(config, metricRegistry);
    }

    @Override
    public void start() throws Exception {
        // do nothing on 'environment.lifecycle().manage(managedDataSource)'
    }

    /*
     * Nevermind multiple calls to super.createPool() (super.start() also has one under the hood)
     * Method super.createPool() built with "initialization on first use" idiom
     */
    @Override
    public synchronized ConnectionPool createPool() throws SQLException {
        if (pool == null) { // is first pool initialization
            try {
                // super.start() will call createPool() method, so we must initialize pool first
                // to ensure single initialization and not run into recursive loop.
                ConnectionPool connectionPool = super.createPool();
                if (connectionPool == null) {
                    // sanity check. must never happen, but taken into account all variety of jdbc providers...
                    return null;
                }
                super.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return super.createPool();
    }

}
