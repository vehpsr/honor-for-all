package com.honor.forall.exception;

import org.eclipse.jetty.http.HttpStatus;

@SuppressWarnings("serial")
public class HonorServiceException extends HonorAppException {

    public HonorServiceException(String status) {
        super(HttpStatus.BAD_REQUEST_400, status);
    }

    public HonorServiceException(String status, Exception cause) {
        super(HttpStatus.BAD_REQUEST_400, status, cause);
    }

}
