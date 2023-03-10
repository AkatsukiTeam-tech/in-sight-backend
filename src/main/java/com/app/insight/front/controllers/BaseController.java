package com.app.insight.front.controllers;

import com.app.insight.front.exceptions.ErrorDtoException;
import com.app.insight.web.rest.errors.*;
import javassist.NotFoundException;
import javassist.tools.rmi.ObjectNotFoundException;
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

    @ExceptionHandler({NotFoundException.class, ObjectNotFoundException.class})
    public ResponseEntity<ErrorDto> errorApiException(Exception exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError(ApiErrors.ObjectNotFound.getCode());
        errorDto.setDetail(exception.getMessage());
        errorDto.setStatus(404);
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(404).body(errorDto);
    }

    @ExceptionHandler(UnauthorizedError.class)
    public ResponseEntity<ErrorDto> errorApiException(UnauthorizedError exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError(ApiErrors.Unauthorized.getCode());
        errorDto.setDetail(exception.getMessage());
        errorDto.setStatus(401);
        return ResponseEntity.status(401).body(errorDto);
    }

    @ExceptionHandler(ErrorDtoException.class)
    public ResponseEntity<ErrorDto> errorDtoResponseEntity(ErrorDtoException exception) {
        int status = exception.getErrorDto().getStatus();
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(status != 0 ? status : 500).body(exception.getErrorDto());
    }
}
