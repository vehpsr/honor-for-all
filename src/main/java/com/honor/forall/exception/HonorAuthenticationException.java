package com.honor.forall.exception;

import org.eclipse.jetty.http.HttpStatus;

@SuppressWarnings("serial")
public class HonorAuthenticationException extends HonorAppException {

    public HonorAuthenticationException(String message) {
        super(HttpStatus.UNAUTHORIZED_401, message);
    }

    public HonorAuthenticationException(String message, Exception cause) {
        super(HttpStatus.UNAUTHORIZED_401, message, cause);
    }

}
