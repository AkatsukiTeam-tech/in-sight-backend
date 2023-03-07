package com.app.insight.front.exceptions;

import com.app.insight.web.rest.errors.ErrorDto;

public class ErrorDtoException extends RuntimeException {

    ErrorDto errorDto;

    public ErrorDtoException(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }

    public ErrorDto getErrorDto() {
        return errorDto;
    }

    public void setErrorDto(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }
}
