package com.app.insight.web.rest.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

public class ErrorDto {

    int status;

    private String detail;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    private LocalDateTime timestamp = LocalDateTime.now();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
