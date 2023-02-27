package com.app.insight.service.dto;

import com.app.insight.domain.enumeration.ParentsEnum;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.app.insight.domain.ParentsNumber} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParentsNumberDTO implements Serializable {

    private Long id;

    @NotNull
    private ParentsEnum role;

    @NotNull
    private String number;

    private AppUserDTO appUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParentsEnum getRole() {
        return role;
    }

    public void setRole(ParentsEnum role) {
        this.role = role;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public AppUserDTO getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserDTO appUser) {
        this.appUser = appUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParentsNumberDTO)) {
            return false;
        }

        ParentsNumberDTO parentsNumberDTO = (ParentsNumberDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, parentsNumberDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParentsNumberDTO{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", number='" + getNumber() + "'" +
            ", appUser=" + getAppUser() +
            "}";
    }
}
