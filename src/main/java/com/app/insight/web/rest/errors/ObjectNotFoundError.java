package com.app.insight.web.rest.errors;

public class ObjectNotFoundError extends ApiException {
    ApiErrors apiError = ApiErrors.ObjectNotFound;

    public ObjectNotFoundError() {}

    public ObjectNotFoundError(String message) {
        super(message);
    }

    @Override
    public ApiErrors getApiError() {
        return apiError;
    }
}
