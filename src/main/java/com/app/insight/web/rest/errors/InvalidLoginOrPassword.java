package com.app.insight.web.rest.errors;

public class InvalidLoginOrPassword extends ApiException {

    ApiErrors apiError = ApiErrors.InvalidLoginOrPassword;

    @Override
    public ApiErrors getApiError() {
        return apiError;
    }
}
