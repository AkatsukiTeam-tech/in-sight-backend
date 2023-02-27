package com.app.insight.web.rest.errors;

public class UserBlockedError extends ApiException {

    ApiErrors apiError = ApiErrors.UserBlockedError;

    @Override
    public ApiErrors getApiError() {
        return apiError;
    }
}
