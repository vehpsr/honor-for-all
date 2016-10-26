package com.honor.forall.db;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.db.ManagedPooledDataSource;

/**
 * Builds lazy (connect on first usage) instance of managed pooled datasource
 */
public class LazyDataSourceFactory extends DataSourceFactory {

    @JsonProperty
    private boolean isLazy = true;

    @Override
    public ManagedDataSource build(MetricRegistry metricRegistry, String name) {
        ManagedDataSource managedDataSource = super.build(metricRegistry, name);

        if (!isLazy) {
            return super.build(metricRegistry, name);
        }

        if (managedDataSource instanceof ManagedPooledDataSource) {
            /* of course we can't depend on it by method protocol, but right now
             * (currently used version of dropwizard 0.7.1 as well as last currently available 0.8.1)
             * exactly this implementation will be returned by super.build(MetricRegistry mr, String name) method
             */
            ManagedPooledDataSource managedPooledDataSource = (ManagedPooledDataSource) managedDataSource;
            return new LazyManagedDataSource(managedPooledDataSource.getPoolProperties(), metricRegistry);
        }

        throw new AssertionError("You can't use this DataSourceFactory implementation with your dropwizard version");
    }

}
