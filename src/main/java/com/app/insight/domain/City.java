package com.app.insight.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A City.
 */
@Entity
@Table(name = "city")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "city")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "appUsers", "city" }, allowSetters = true)
    private Set<Region> regions = new HashSet<>();

    @OneToMany(mappedBy = "city")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "parentsNumbers",
            "views",
            "comments",
            "commentAnswers",
            "taskAnswers",
            "mediaFiles",
            "optionUsers",
            "coinsUserHistories",
            "appRoles",
            "subgroups",
            "city",
            "region",
            "school",
        },
        allowSetters = true
    )
    private Set<AppUser> appUsers = new HashSet<>();

    @OneToMany(mappedBy = "city")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comments", "specializations", "city" }, allowSetters = true)
    private Set<University> universities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public City id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public City name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Region> getRegions() {
        return this.regions;
    }

    public void setRegions(Set<Region> regions) {
        if (this.regions != null) {
            this.regions.forEach(i -> i.setCity(null));
        }
        if (regions != null) {
            regions.forEach(i -> i.setCity(this));
        }
        this.regions = regions;
    }

    public City regions(Set<Region> regions) {
        this.setRegions(regions);
        return this;
    }

    public City addRegion(Region region) {
        this.regions.add(region);
        region.setCity(this);
        return this;
    }

    public City removeRegion(Region region) {
        this.regions.remove(region);
        region.setCity(null);
        return this;
    }

    public Set<AppUser> getAppUsers() {
        return this.appUsers;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        if (this.appUsers != null) {
            this.appUsers.forEach(i -> i.setCity(null));
        }
        if (appUsers != null) {
            appUsers.forEach(i -> i.setCity(this));
        }
        this.appUsers = appUsers;
    }

    public City appUsers(Set<AppUser> appUsers) {
        this.setAppUsers(appUsers);
        return this;
    }

    public City addAppUser(AppUser appUser) {
        this.appUsers.add(appUser);
        appUser.setCity(this);
        return this;
    }

    public City removeAppUser(AppUser appUser) {
        this.appUsers.remove(appUser);
        appUser.setCity(null);
        return this;
    }

    public Set<University> getUniversities() {
        return this.universities;
    }

    public void setUniversities(Set<University> universities) {
        if (this.universities != null) {
            this.universities.forEach(i -> i.setCity(null));
        }
        if (universities != null) {
            universities.forEach(i -> i.setCity(this));
        }
        this.universities = universities;
    }

    public City universities(Set<University> universities) {
        this.setUniversities(universities);
        return this;
    }

    public City addUniversity(University university) {
        this.universities.add(university);
        university.setCity(this);
        return this;
    }

    public City removeUniversity(University university) {
        this.universities.remove(university);
        university.setCity(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof City)) {
            return false;
        }
        return id != null && id.equals(((City) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "City{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
