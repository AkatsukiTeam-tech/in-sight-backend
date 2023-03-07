package com.app.insight.service.dto;

/**
 * DTO для сгенерированного пароля
 */
public class GeneratedPasswordDto {

    private String password;

    public GeneratedPasswordDto() {
    }

    public GeneratedPasswordDto(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
