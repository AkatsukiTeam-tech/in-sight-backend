package com.app.insight.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.app.insight.domain.Subgroup} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubgroupDTO implements Serializable {

    private Long id;

    private String name;

    private GroupDTO group;

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

    public GroupDTO getGroup() {
        return group;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubgroupDTO)) {
            return false;
        }

        SubgroupDTO subgroupDTO = (SubgroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subgroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubgroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", group=" + getGroup() +
            "}";
    }
}
