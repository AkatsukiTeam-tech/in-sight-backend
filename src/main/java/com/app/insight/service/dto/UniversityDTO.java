package com.app.insight.service.dto;

import com.app.insight.domain.enumeration.UniversityStatusEnum;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.app.insight.domain.University} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UniversityDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer middlePrice;

    private UniversityStatusEnum status;

    private Boolean militaryDepartment;

    private String description;

    private Integer code;

    private Set<SpecializationDTO> specializations = new HashSet<>();

    private CityDTO city;

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

    public Integer getMiddlePrice() {
        return middlePrice;
    }

    public void setMiddlePrice(Integer middlePrice) {
        this.middlePrice = middlePrice;
    }

    public UniversityStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UniversityStatusEnum status) {
        this.status = status;
    }

    public Boolean getMilitaryDepartment() {
        return militaryDepartment;
    }

    public void setMilitaryDepartment(Boolean militaryDepartment) {
        this.militaryDepartment = militaryDepartment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Set<SpecializationDTO> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Set<SpecializationDTO> specializations) {
        this.specializations = specializations;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UniversityDTO)) {
            return false;
        }

        UniversityDTO universityDTO = (UniversityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, universityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UniversityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", middlePrice=" + getMiddlePrice() +
            ", status='" + getStatus() + "'" +
            ", militaryDepartment='" + getMilitaryDepartment() + "'" +
            ", description='" + getDescription() + "'" +
            ", code=" + getCode() +
            ", specializations=" + getSpecializations() +
            ", city=" + getCity() +
            "}";
    }
}
