package com.app.insight.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.app.insight.domain.OptionUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OptionUserDTO implements Serializable {

    private Long id;

    private ZonedDateTime dateTime;

    private AppUserDTO appUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
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
        if (!(o instanceof OptionUserDTO)) {
            return false;
        }

        OptionUserDTO optionUserDTO = (OptionUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, optionUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OptionUserDTO{" +
            "id=" + getId() +
            ", dateTime='" + getDateTime() + "'" +
            ", appUser=" + getAppUser() +
            "}";
    }
}
