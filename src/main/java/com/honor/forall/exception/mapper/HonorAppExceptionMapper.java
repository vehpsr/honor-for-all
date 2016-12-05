package com.honor.forall.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honor.forall.exception.HonorAppException;
import com.honor.forall.exception.response.ErrorResponse;

public class HonorAppExceptionMapper implements ExceptionMapper<HonorAppException> {

    private static final Logger LOG = LoggerFactory.getLogger(HonorAppExceptionMapper.class);

    @Override
    public Response toResponse(HonorAppException exception) {
        int code = exception.getCode();
        String status = exception.getStatus();
        LOG.error("Fail to process request: {} {}", code, status, exception);
        return ErrorResponse.build(code, status);
    }

}
