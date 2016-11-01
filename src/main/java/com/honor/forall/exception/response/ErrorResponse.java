package com.honor.forall.exception.response;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

    @JsonProperty
    private String message;

    public static Response build(int status, String message) {
        return Response.status(status).entity(new ErrorResponse().withMessage(message)).build();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorResponse withMessage(String message) {
        setMessage(message);
        return this;
    }

}
