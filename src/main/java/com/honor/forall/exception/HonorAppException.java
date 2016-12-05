package com.honor.forall.exception;

import org.eclipse.jetty.http.HttpStatus;

@SuppressWarnings("serial")
public abstract class HonorAppException extends RuntimeException {

    private final int code;
    private final String status;

    public HonorAppException(String status) {
        this(HttpStatus.INTERNAL_SERVER_ERROR_500, status);
    }

    public HonorAppException(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public HonorAppException(int code, String status, Exception cause) {
        super(cause);
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

}
