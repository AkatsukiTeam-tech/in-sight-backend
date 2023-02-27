package com.app.insight.domain;

import com.app.insight.domain.enumeration.DemandEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Specialization.
 */
@Entity
@Table(name = "specialization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Specialization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_specialization")
    @SequenceGenerator(name = "s_specialization", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "grand_score")
    private Integer grandScore;

    @Column(name = "grand_count")
    private Integer grandCount;

    @Column(name = "average_salary")
    private Integer averageSalary;

    @Enumerated(EnumType.STRING)
    @Column(name = "demand")
    private DemandEnum demand;

    @Column(name = "code")
    private Integer code;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "specializations")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comments", "specializations", "city" }, allowSetters = true)
    private Set<University> universities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Specialization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Specialization name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrandScore() {
        return this.grandScore;
    }

    public Specialization grandScore(Integer grandScore) {
        this.setGrandScore(grandScore);
        return this;
    }

    public void setGrandScore(Integer grandScore) {
        this.grandScore = grandScore;
    }

    public Integer getGrandCount() {
        return this.grandCount;
    }

    public Specialization grandCount(Integer grandCount) {
        this.setGrandCount(grandCount);
        return this;
    }

    public void setGrandCount(Integer grandCount) {
        this.grandCount = grandCount;
    }

    public Integer getAverageSalary() {
        return this.averageSalary;
    }

    public Specialization averageSalary(Integer averageSalary) {
        this.setAverageSalary(averageSalary);
        return this;
    }

    public void setAverageSalary(Integer averageSalary) {
        this.averageSalary = averageSalary;
    }

    public DemandEnum getDemand() {
        return this.demand;
    }

    public Specialization demand(DemandEnum demand) {
        this.setDemand(demand);
        return this;
    }

    public void setDemand(DemandEnum demand) {
        this.demand = demand;
    }

    public Integer getCode() {
        return this.code;
    }

    public Specialization code(Integer code) {
        this.setCode(code);
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public Specialization description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<University> getUniversities() {
        return this.universities;
    }

    public void setUniversities(Set<University> universities) {
        if (this.universities != null) {
            this.universities.forEach(i -> i.removeSpecialization(this));
        }
        if (universities != null) {
            universities.forEach(i -> i.addSpecialization(this));
        }
        this.universities = universities;
    }

    public Specialization universities(Set<University> universities) {
        this.setUniversities(universities);
        return this;
    }

    public Specialization addUniversity(University university) {
        this.universities.add(university);
        university.getSpecializations().add(this);
        return this;
    }

    public Specialization removeUniversity(University university) {
        this.universities.remove(university);
        university.getSpecializations().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Specialization)) {
            return false;
        }
        return id != null && id.equals(((Specialization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Specialization{" +
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
