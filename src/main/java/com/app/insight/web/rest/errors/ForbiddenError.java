package com.app.insight.web.rest.errors;

public class ForbiddenError extends ApiException {

    ApiErrors apiError = ApiErrors.Forbidden;

    @Override
    public ApiErrors getApiError() {
        return apiError;
    }
}
