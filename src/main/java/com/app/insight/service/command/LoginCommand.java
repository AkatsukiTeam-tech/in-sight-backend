package com.app.insight.service.command;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginCommand {

    @NotNull
    @Size(min = 1, max = 50)
    private String login;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
