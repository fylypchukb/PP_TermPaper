package com.example.Knights.Domain.Response;

import org.springframework.http.HttpStatus;

public class RestException extends Exception{

    private HttpStatus statusCode;
    public RestException(HttpStatus statusCode, String message)
    {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
