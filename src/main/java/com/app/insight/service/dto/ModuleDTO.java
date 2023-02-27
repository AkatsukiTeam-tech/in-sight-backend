package com.app.insight.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.app.insight.domain.Module} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModuleDTO implements Serializable {

    private Long id;

    private String name;

    private SubjectDTO subject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModuleDTO)) {
            return false;
        }

        ModuleDTO moduleDTO = (ModuleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, moduleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModuleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", subject=" + getSubject() +
            "}";
    }
}
