package com.app.insight.web.rest.errors;

public class UserNotFoundError extends ApiException {

    ApiErrors apiError = ApiErrors.UserEntityNotFound;

    public UserNotFoundError() {}

    public UserNotFoundError(String message) {
        super(message);
    }

    @Override
    public ApiErrors getApiError() {
        return apiError;
    }
}
