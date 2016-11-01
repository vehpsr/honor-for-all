package com.honor.forall.exception.mapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.honor.forall.exception.response.ErrorResponse;

public class UnhandledExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOG = LoggerFactory.getLogger(UnhandledExceptionMapper.class);
    private static final String ERR_MSG_500 = "Internal server error";

    @Override
    public Response toResponse(Exception exception) {
        // Return 400 with error message on receiving invalid field values in request body
        if (exception instanceof InvalidFormatException) {
            LOG.error("Deserialization error", exception);
            return ErrorResponse.build(HttpStatus.BAD_REQUEST_400,
                    ((InvalidFormatException) exception).getOriginalMessage());
        }

        if (exception instanceof WebApplicationException) {
            Response webResponse = ((WebApplicationException) exception).getResponse();
            if (webResponse.getStatus() != HttpStatus.INTERNAL_SERVER_ERROR_500) {
                return webResponse;
            }
        }

        LOG.error(exception.getMessage(), exception);
        return ErrorResponse.build(HttpStatus.INTERNAL_SERVER_ERROR_500, ERR_MSG_500);
    }


}
