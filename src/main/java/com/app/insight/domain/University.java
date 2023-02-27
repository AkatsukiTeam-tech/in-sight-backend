package com.app.insight.domain;

import com.app.insight.domain.enumeration.UniversityStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A University.
 */
@Entity
@Table(name = "university")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class University implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "middle_price", nullable = false)
    private Integer middlePrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UniversityStatusEnum status;

    @Column(name = "military_department")
    private Boolean militaryDepartment;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private Integer code;

    @OneToMany(mappedBy = "university")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "commentAnswers", "post", "university", "appUser" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_university__specialization",
        joinColumns = @JoinColumn(name = "university_id"),
        inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "universities" }, allowSetters = true)
    private Set<Specialization> specializations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "regions", "appUsers", "universities" }, allowSetters = true)
    private City city;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public University id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public University name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMiddlePrice() {
        return this.middlePrice;
    }

    public University middlePrice(Integer middlePrice) {
        this.setMiddlePrice(middlePrice);
        return this;
    }

    public void setMiddlePrice(Integer middlePrice) {
        this.middlePrice = middlePrice;
    }

    public UniversityStatusEnum getStatus() {
        return this.status;
    }

    public University status(UniversityStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(UniversityStatusEnum status) {
        this.status = status;
    }

    public Boolean getMilitaryDepartment() {
        return this.militaryDepartment;
    }

    public University militaryDepartment(Boolean militaryDepartment) {
        this.setMilitaryDepartment(militaryDepartment);
        return this;
    }

    public void setMilitaryDepartment(Boolean militaryDepartment) {
        this.militaryDepartment = militaryDepartment;
    }

    public String getDescription() {
        return this.description;
    }

    public University description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCode() {
        return this.code;
    }

    public University code(Integer code) {
        this.setCode(code);
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setUniversity(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setUniversity(this));
        }
        this.comments = comments;
    }

    public University comments(Set<Comment> comments) {
        this.setComments(comments);
        return this;
    }

    public University addComment(Comment comment) {
        this.comments.add(comment);
        comment.setUniversity(this);
        return this;
    }

    public University removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setUniversity(null);
        return this;
    }

    public Set<Specialization> getSpecializations() {
        return this.specializations;
    }

    public void setSpecializations(Set<Specialization> specializations) {
        this.specializations = specializations;
    }

    public University specializations(Set<Specialization> specializations) {
        this.setSpecializations(specializations);
        return this;
    }

    public University addSpecialization(Specialization specialization) {
        this.specializations.add(specialization);
        specialization.getUniversities().add(this);
        return this;
    }

    public University removeSpecialization(Specialization specialization) {
        this.specializations.remove(specialization);
        specialization.getUniversities().remove(this);
        return this;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public University city(City city) {
        this.setCity(city);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof University)) {
            return false;
        }
        return id != null && id.equals(((University) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "University{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", middlePrice=" + getMiddlePrice() +
            ", status='" + getStatus() + "'" +
            ", militaryDepartment='" + getMilitaryDepartment() + "'" +
            ", description='" + getDescription() + "'" +
            ", code=" + getCode() +
            "}";
    }
}
