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
 * A Region.
 */
@Entity
@Table(name = "region")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_region")
    @SequenceGenerator(name = "s_region", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "region")
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

    @OneToMany(mappedBy = "region")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "appUsers", "universities" }, allowSetters = true)
    private Set<City> cities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Region id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Region name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AppUser> getAppUsers() {
        return this.appUsers;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        if (this.appUsers != null) {
            this.appUsers.forEach(i -> i.setRegion(null));
        }
        if (appUsers != null) {
            appUsers.forEach(i -> i.setRegion(this));
        }
        this.appUsers = appUsers;
    }

    public Region appUsers(Set<AppUser> appUsers) {
        this.setAppUsers(appUsers);
        return this;
    }

    public Region addAppUser(AppUser appUser) {
        this.appUsers.add(appUser);
        appUser.setRegion(this);
        return this;
    }

    public Region removeAppUser(AppUser appUser) {
        this.appUsers.remove(appUser);
        appUser.setRegion(null);
        return this;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        if (this.cities != null) {
            this.cities.forEach(i -> i.setRegion(null));
        }
        if (cities != null) {
            cities.forEach(i -> i.setRegion(this));
        }
        this.cities = cities;
    }

    public Region regions(Set<City> cities) {
        this.setCities(cities);
        return this;
    }

    public Region addCity(City city) {
        this.getCities().add(city);
        return this;
    }

    public Region removeCity(City city) {
        this.cities.remove(city);
        city.setRegion(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Region)) {
            return false;
        }
        return id != null && id.equals(((Region) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Region{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
