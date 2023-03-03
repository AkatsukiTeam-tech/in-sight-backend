package com.app.insight.service.command;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ResetPasswordCommand {

    @NotNull
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!?~*@#$&])(?=\\S+$).{8,}$",
        message = "Пароль должен содержать как минимум одну прописную букву, одну строчную букву и одну цифру и одну из символов (!, ?, @, #, $, &, *, ~) и не менее 8 символов."
    )
    private String password;

    @NotEmpty
    private String passwordConfirmation;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
