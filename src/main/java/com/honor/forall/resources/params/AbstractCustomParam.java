package com.honor.forall.resources.params;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import io.dropwizard.jersey.params.AbstractParam;

public abstract class AbstractCustomParam<T> extends AbstractParam<T> {

    protected AbstractCustomParam(String input) {
        super(input);
    }

    @Override
    public Response error(String input, Exception e) {
        if (e instanceof WebApplicationException) {
            return ((WebApplicationException) e).getResponse();
        }
        return super.error(input, e);
    }

}
