package com.app.insight.service.dto;

import java.util.HashSet;
import java.util.Set;

public class SecureUserDto {
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private Integer age;

    private String email;

    private String login;

    private String phoneNumber;

    private String iin;

    private Integer coins;

    private Integer entResult;

    private Set<AppRoleDTO> appRoles = new HashSet<>();

    private Set<SubgroupDTO> subgroups = new HashSet<>();

    private CityDTO city;

    private SchoolDTO school;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public Integer getEntResult() {
        return entResult;
    }

    public void setEntResult(Integer entResult) {
        this.entResult = entResult;
    }

    public Set<AppRoleDTO> getAppRoles() {
        return appRoles;
    }

    public void setAppRoles(Set<AppRoleDTO> appRoles) {
        this.appRoles = appRoles;
    }

    public Set<SubgroupDTO> getSubgroups() {
        return subgroups;
    }

    public void setSubgroups(Set<SubgroupDTO> subgroups) {
        this.subgroups = subgroups;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public SchoolDTO getSchool() {
        return school;
    }

    public void setSchool(SchoolDTO school) {
        this.school = school;
    }
}
