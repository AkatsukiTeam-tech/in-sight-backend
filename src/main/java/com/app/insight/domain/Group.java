package com.app.insight.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Group.
 */
@Entity
@Table(name = "jhi_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "group")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "schedules", "group", "appUsers" }, allowSetters = true)
    private Set<Subgroup> subgroups = new HashSet<>();

    @ManyToMany(mappedBy = "groups")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "modules", "groups", "question" }, allowSetters = true)
    private Set<Subject> subjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Group id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Group name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Subgroup> getSubgroups() {
        return this.subgroups;
    }

    public void setSubgroups(Set<Subgroup> subgroups) {
        if (this.subgroups != null) {
            this.subgroups.forEach(i -> i.setGroup(null));
        }
        if (subgroups != null) {
            subgroups.forEach(i -> i.setGroup(this));
        }
        this.subgroups = subgroups;
    }

    public Group subgroups(Set<Subgroup> subgroups) {
        this.setSubgroups(subgroups);
        return this;
    }

    public Group addSubgroup(Subgroup subgroup) {
        this.subgroups.add(subgroup);
        subgroup.setGroup(this);
        return this;
    }

    public Group removeSubgroup(Subgroup subgroup) {
        this.subgroups.remove(subgroup);
        subgroup.setGroup(null);
        return this;
    }

    public Set<Subject> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        if (this.subjects != null) {
            this.subjects.forEach(i -> i.removeGroup(this));
        }
        if (subjects != null) {
            subjects.forEach(i -> i.addGroup(this));
        }
        this.subjects = subjects;
    }

    public Group subjects(Set<Subject> subjects) {
        this.setSubjects(subjects);
        return this;
    }

    public Group addSubject(Subject subject) {
        this.subjects.add(subject);
        subject.getGroups().add(this);
        return this;
    }

    public Group removeSubject(Subject subject) {
        this.subjects.remove(subject);
        subject.getGroups().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Group)) {
            return false;
        }
        return id != null && id.equals(((Group) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Group{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
