package com.app.insight.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Subgroup.
 */
@Entity
@Table(name = "subgroup")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Subgroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_subgroup")
    @SequenceGenerator(name = "s_subgroup", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "subgroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "subgroup", "module" }, allowSetters = true)
    private Set<Schedule> schedules = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "subgroups", "subjects" }, allowSetters = true)
    private Group group;

    @ManyToMany(mappedBy = "subgroups")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Subgroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Subgroup name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Schedule> getSchedules() {
        return this.schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        if (this.schedules != null) {
            this.schedules.forEach(i -> i.setSubgroup(null));
        }
        if (schedules != null) {
            schedules.forEach(i -> i.setSubgroup(this));
        }
        this.schedules = schedules;
    }

    public Subgroup schedules(Set<Schedule> schedules) {
        this.setSchedules(schedules);
        return this;
    }

    public Subgroup addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setSubgroup(this);
        return this;
    }

    public Subgroup removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setSubgroup(null);
        return this;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Subgroup group(Group group) {
        this.setGroup(group);
        return this;
    }

    public Set<AppUser> getAppUsers() {
        return this.appUsers;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        if (this.appUsers != null) {
            this.appUsers.forEach(i -> i.removeSubgroup(this));
        }
        if (appUsers != null) {
            appUsers.forEach(i -> i.addSubgroup(this));
        }
        this.appUsers = appUsers;
    }

    public Subgroup appUsers(Set<AppUser> appUsers) {
        this.setAppUsers(appUsers);
        return this;
    }

    public Subgroup addAppUser(AppUser appUser) {
        this.appUsers.add(appUser);
        appUser.getSubgroups().add(this);
        return this;
    }

    public Subgroup removeAppUser(AppUser appUser) {
        this.appUsers.remove(appUser);
        appUser.getSubgroups().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subgroup)) {
            return false;
        }
        return id != null && id.equals(((Subgroup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Subgroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
