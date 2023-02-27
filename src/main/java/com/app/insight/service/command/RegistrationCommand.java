package com.app.insight.service.command;

import com.app.insight.domain.enumeration.AppRoleTypeEnum;
import com.app.insight.service.dto.*;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RegistrationCommand {

    @Email
    @NotNull
    private String email;

    @NotNull
    private String login;

    @NotNull
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!?~*@#$&])(?=\\S+$).{8,}$",
        message = "Пароль должен содержать как минимум одну прописную букву, одну строчную букву и одну цифру и одну из символов (!, ?, @, #, $, &, *, ~) и не менее 8 символов."
    )
    private String password;

    @NotEmpty
    private String passwordConfirmation;

    @NotNull
    @Pattern(regexp = "^[0-9]{11}$", message = "Номер тел. должен состоять из 11 цифр без знака '+'")
    private String phoneNumber;

    @NotEmpty
    private String firstName;

    private String middleName;

    @NotEmpty
    private String lastName;

    @NotNull
    private Integer age;

    @NotNull
    private Integer entResult;

    @NotEmpty
    private String iin;

    @NotNull
    private CityDTO city;

    @NotNull
    private RegionDTO region;

    @NotNull
    private SchoolDTO school;

    @NotNull
    private AppRoleTypeEnum role;

    @NotNull
    private Set<ParentsNumberDTO> parentsNumbers;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getEntResult() {
        return entResult;
    }

    public void setEntResult(Integer entResult) {
        this.entResult = entResult;
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }

    public SchoolDTO getSchool() {
        return school;
    }

    public void setSchool(SchoolDTO school) {
        this.school = school;
    }

    public Set<ParentsNumberDTO> getParentsNumbers() {
        return parentsNumbers;
    }

    public void setParentsNumbers(Set<ParentsNumberDTO> parentsNumbers) {
        this.parentsNumbers = parentsNumbers;
    }

    public AppRoleTypeEnum getRole() {
        return role;
    }

    public void setRole(AppRoleTypeEnum role) {
        this.role = role;
    }
}
