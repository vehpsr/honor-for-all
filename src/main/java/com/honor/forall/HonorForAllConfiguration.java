package com.honor.forall;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.honor.forall.db.LazyDataSourceFactory;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class HonorForAllConfiguration extends Configuration {

    @JsonProperty
    @NotNull
    private LazyDataSourceFactory database;

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

}
