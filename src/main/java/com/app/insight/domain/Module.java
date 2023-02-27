package com.app.insight.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Module.
 */
@Entity
@Table(name = "module")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_module")
    @SequenceGenerator(name = "s_module", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "module")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "subgroup", "module" }, allowSetters = true)
    private Set<Schedule> schedules = new HashSet<>();

    @OneToMany(mappedBy = "module")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "questions", "module" }, allowSetters = true)
    private Set<AppTest> appTests = new HashSet<>();

    @OneToMany(mappedBy = "module")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mediaFiles", "taskAnswers", "module" }, allowSetters = true)
    private Set<Task> tasks = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "modules", "groups", "question" }, allowSetters = true)
    private Subject subject;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Module id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Module name(String name) {
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
            this.schedules.forEach(i -> i.setModule(null));
        }
        if (schedules != null) {
            schedules.forEach(i -> i.setModule(this));
        }
        this.schedules = schedules;
    }

    public Module schedules(Set<Schedule> schedules) {
        this.setSchedules(schedules);
        return this;
    }

    public Module addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setModule(this);
        return this;
    }

    public Module removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setModule(null);
        return this;
    }

    public Set<AppTest> getAppTests() {
        return this.appTests;
    }

    public void setAppTests(Set<AppTest> appTests) {
        if (this.appTests != null) {
            this.appTests.forEach(i -> i.setModule(null));
        }
        if (appTests != null) {
            appTests.forEach(i -> i.setModule(this));
        }
        this.appTests = appTests;
    }

    public Module appTests(Set<AppTest> appTests) {
        this.setAppTests(appTests);
        return this;
    }

    public Module addAppTest(AppTest appTest) {
        this.appTests.add(appTest);
        appTest.setModule(this);
        return this;
    }

    public Module removeAppTest(AppTest appTest) {
        this.appTests.remove(appTest);
        appTest.setModule(null);
        return this;
    }

    public Set<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(Set<Task> tasks) {
        if (this.tasks != null) {
            this.tasks.forEach(i -> i.setModule(null));
        }
        if (tasks != null) {
            tasks.forEach(i -> i.setModule(this));
        }
        this.tasks = tasks;
    }

    public Module tasks(Set<Task> tasks) {
        this.setTasks(tasks);
        return this;
    }

    public Module addTask(Task task) {
        this.tasks.add(task);
        task.setModule(this);
        return this;
    }

    public Module removeTask(Task task) {
        this.tasks.remove(task);
        task.setModule(null);
        return this;
    }

    public Subject getSubject() {
        return this.subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Module subject(Subject subject) {
        this.setSubject(subject);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Module)) {
            return false;
        }
        return id != null && id.equals(((Module) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Module{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
