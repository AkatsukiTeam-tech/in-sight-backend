package com.app.insight.web.rest.errors;

public class ValidationError extends ApiException {
    ApiErrors apiError = ApiErrors.ValidationError;

    public ValidationError() {}

    public ValidationError(String message) {
        super(message);
    }

    @Override
    public ApiErrors getApiError() {
        return apiError;
    }
}
