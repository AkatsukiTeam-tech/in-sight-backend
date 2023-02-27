package com.app.insight.web.rest.errors;

public abstract class ApiException extends RuntimeException {

    public ApiException() {}

    public ApiException(String message) {
        super(message);
    }

    public abstract ApiErrors getApiError();
}
