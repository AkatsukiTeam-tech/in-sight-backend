package com.app.insight.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.app.insight.domain.AppUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUserDTO implements Serializable {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private Boolean isActive;

    private Boolean isDeleted;

    private Integer age;

    private String email;

    private String login;

    private String password;

    private ZonedDateTime regDateTime;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String iin;

    private Integer coins;

    private Integer entResult;

    private Set<AppRoleDTO> appRoles = new HashSet<>();

    private Set<SubgroupDTO> subgroups = new HashSet<>();

    private CityDTO city;

    private RegionDTO region;

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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ZonedDateTime getRegDateTime() {
        return regDateTime;
    }

    public void setRegDateTime(ZonedDateTime regDateTime) {
        this.regDateTime = regDateTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUserDTO)) {
            return false;
        }

        AppUserDTO appUserDTO = (AppUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUserDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", age=" + getAge() +
            ", email='" + getEmail() + "'" +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", regDateTime='" + getRegDateTime() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", iin='" + getIin() + "'" +
            ", coins=" + getCoins() +
            ", entResult=" + getEntResult() +
            ", appRoles=" + getAppRoles() +
            ", subgroups=" + getSubgroups() +
            ", city=" + getCity() +
            ", region=" + getRegion() +
            ", school=" + getSchool() +
            "}";
    }
}
