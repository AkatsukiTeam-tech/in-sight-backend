package com.app.insight.service.dto;

import com.app.insight.domain.enumeration.AppTestTypeEnum;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.app.insight.domain.AppTest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppTestDTO implements Serializable {

    private Long id;

    private AppTestTypeEnum type;

    private String name;

    private String description;

    private ModuleDTO module;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppTestTypeEnum getType() {
        return type;
    }

    public void setType(AppTestTypeEnum type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ModuleDTO getModule() {
        return module;
    }

    public void setModule(ModuleDTO module) {
        this.module = module;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppTestDTO)) {
            return false;
        }

        AppTestDTO appTestDTO = (AppTestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appTestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppTestDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", module=" + getModule() +
            "}";
    }
}
