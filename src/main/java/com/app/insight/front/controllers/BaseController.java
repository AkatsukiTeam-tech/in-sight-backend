package com.app.insight.front.controllers;

import com.app.insight.web.rest.errors.ApiErrors;
import com.app.insight.web.rest.errors.ApiException;
import com.app.insight.web.rest.errors.ErrorDto;
import com.app.insight.web.rest.errors.ForbiddenError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {

    private final Logger log = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> errorMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);

        ErrorDto errorDto = new ErrorDto();
        errorDto.setError(ApiErrors.BadRequest.getCode());
        errorDto.setDetail("Invalid request");
        errorDto.setStatus(ApiErrors.BadRequest.getStatus());
        return ResponseEntity.status(400).body(errorDto);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDto> errorApiException(ApiException exception) {
        log.error(exception.getMessage(), exception);

        ErrorDto errorDto = new ErrorDto();
        errorDto.setError(exception.getApiError().getCode());
        errorDto.setDetail(exception.getMessage() != null ? exception.getMessage() : exception.getApiError().getDefaultDescription());
        errorDto.setStatus(exception.getApiError().getStatus());
        return ResponseEntity.status(400).body(errorDto);
    }

    @ExceptionHandler(ForbiddenError.class)
    public ResponseEntity<ErrorDto> errorApiException(ForbiddenError exception) {
        log.error(exception.getMessage(), exception);

        ErrorDto errorDto = new ErrorDto();
        errorDto.setError(ApiErrors.Forbidden.getCode());
        errorDto.setDetail(exception.getMessage());
        errorDto.setStatus(ApiErrors.Forbidden.getStatus());
        return ResponseEntity.status(ApiErrors.Forbidden.getStatus()).body(errorDto);
    }
}
