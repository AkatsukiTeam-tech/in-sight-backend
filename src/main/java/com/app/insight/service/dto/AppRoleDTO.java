package com.app.insight.service.dto;

import com.app.insight.domain.enumeration.AppRoleTypeEnum;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.app.insight.domain.AppRole} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppRoleDTO implements Serializable {

    private Long id;

    private AppRoleTypeEnum name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppRoleTypeEnum getName() {
        return name;
    }

    public void setName(AppRoleTypeEnum name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppRoleDTO)) {
            return false;
        }

        AppRoleDTO appRoleDTO = (AppRoleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appRoleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppRoleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
