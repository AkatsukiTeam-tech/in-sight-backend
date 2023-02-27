package com.app.insight.service.dto;

import com.app.insight.domain.enumeration.DemandEnum;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.app.insight.domain.Specialization} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SpecializationDTO implements Serializable {

    private Long id;

    private String name;

    private Integer grandScore;

    private Integer grandCount;

    private Integer averageSalary;

    private DemandEnum demand;

    private Integer code;

    private String description;

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

    public Integer getGrandScore() {
        return grandScore;
    }

    public void setGrandScore(Integer grandScore) {
        this.grandScore = grandScore;
    }

    public Integer getGrandCount() {
        return grandCount;
    }

    public void setGrandCount(Integer grandCount) {
        this.grandCount = grandCount;
    }

    public Integer getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(Integer averageSalary) {
        this.averageSalary = averageSalary;
    }

    public DemandEnum getDemand() {
        return demand;
    }

    public void setDemand(DemandEnum demand) {
        this.demand = demand;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpecializationDTO)) {
            return false;
        }

        SpecializationDTO specializationDTO = (SpecializationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, specializationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpecializationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", grandScore=" + getGrandScore() +
            ", grandCount=" + getGrandCount() +
            ", averageSalary=" + getAverageSalary() +
            ", demand='" + getDemand() + "'" +
            ", code=" + getCode() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
