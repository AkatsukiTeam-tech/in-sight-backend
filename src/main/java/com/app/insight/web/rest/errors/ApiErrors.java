package com.app.insight.web.rest.errors;

public enum ApiErrors {
    InvalidSMSCode("invalid_sms_code", 400, "Invalid SMS code"),
    BadRequest("bad_request", 400, "Bad Request"),
    InvalidPhoneNumber("invalid_phone_number", 400, "Enter a valid phone number"),
    InvalidIin("invalid_iin", 400, "IIN validation error"),
    InvalidLoginOrPassword("invalid_login_or_password", 401, "Не верный логин или пароль!"),
    ValidationError("validation_error", 400, "Validation Error"),
    FieldRequired("field_required", 400, "Поле обязательно к заполнению"),
    Unauthorized("unauthorized", 401, "Unauthorized"),
    BadCredentials("bad_credentials", 401, "Bad Credentials"),
    Forbidden("forbidden", 403, "Операция запрещена"),
    ForbiddenAccessToObject("forbidden", 403, "Запрещен доступ к объекту"),
    InvalidProcessState("invalid_process_state", 403, "Invalid Process State"),
    TokenNotValid("token_not_valid", 401, "Token is invalid or expired"),
    ObjectNotFound("object_not_found", 404, "Object not found"),
    ObjectAlreadyExist("object_already_exist", 409, "Object already exist"),
    ImageNotFound("image_not_found", 404, "Image not found"),
    InternalServerError("internal_server_error", 500, "Internal Server Error"),
    UserEntityNotFound("user_not_found_error", 404, "User not found"),
    OrganizationAlreadyExist("organization_already_exist", 409, "Organization already exist"),
    Conflict("conflict", 409, "Conflict"),
    IntegrationError("integration_error", 503, "Integration Error"),
    UserBlockedError("blocked_user", 401, "Пользователь заблокирован. Обратитесь к администратору системы");

    ApiErrors(String code, int status, String defaultDescription) {
        this.code = code;
        this.status = status;
        this.defaultDescription = defaultDescription;
    }

    String code;

    int status;

    String defaultDescription;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDefaultDescription() {
        return defaultDescription;
    }

    public void setDefaultDescription(String defaultDescription) {
        this.defaultDescription = defaultDescription;
    }
}
