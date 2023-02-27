package com.app.insight.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_task")
    @SequenceGenerator(name = "s_task", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private ZonedDateTime deadline;

    @OneToMany(mappedBy = "task")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "appUser", "task", "taskAnswer" }, allowSetters = true)
    private Set<MediaFiles> mediaFiles = new HashSet<>();

    @OneToMany(mappedBy = "task")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mediaFiles", "appUser", "task" }, allowSetters = true)
    private Set<TaskAnswer> taskAnswers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "schedules", "appTests", "tasks", "subject" }, allowSetters = true)
    private Module module;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Task id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public Task description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDeadline() {
        return this.deadline;
    }

    public Task deadline(ZonedDateTime deadline) {
        this.setDeadline(deadline);
        return this;
    }

    public void setDeadline(ZonedDateTime deadline) {
        this.deadline = deadline;
    }

    public Set<MediaFiles> getMediaFiles() {
        return this.mediaFiles;
    }

    public void setMediaFiles(Set<MediaFiles> mediaFiles) {
        if (this.mediaFiles != null) {
            this.mediaFiles.forEach(i -> i.setTask(null));
        }
        if (mediaFiles != null) {
            mediaFiles.forEach(i -> i.setTask(this));
        }
        this.mediaFiles = mediaFiles;
    }

    public Task mediaFiles(Set<MediaFiles> mediaFiles) {
        this.setMediaFiles(mediaFiles);
        return this;
    }

    public Task addMediaFiles(MediaFiles mediaFiles) {
        this.mediaFiles.add(mediaFiles);
        mediaFiles.setTask(this);
        return this;
    }

    public Task removeMediaFiles(MediaFiles mediaFiles) {
        this.mediaFiles.remove(mediaFiles);
        mediaFiles.setTask(null);
        return this;
    }

    public Set<TaskAnswer> getTaskAnswers() {
        return this.taskAnswers;
    }

    public void setTaskAnswers(Set<TaskAnswer> taskAnswers) {
        if (this.taskAnswers != null) {
            this.taskAnswers.forEach(i -> i.setTask(null));
        }
        if (taskAnswers != null) {
            taskAnswers.forEach(i -> i.setTask(this));
        }
        this.taskAnswers = taskAnswers;
    }

    public Task taskAnswers(Set<TaskAnswer> taskAnswers) {
        this.setTaskAnswers(taskAnswers);
        return this;
    }

    public Task addTaskAnswer(TaskAnswer taskAnswer) {
        this.taskAnswers.add(taskAnswer);
        taskAnswer.setTask(this);
        return this;
    }

    public Task removeTaskAnswer(TaskAnswer taskAnswer) {
        this.taskAnswers.remove(taskAnswer);
        taskAnswer.setTask(null);
        return this;
    }

    public Module getModule() {
        return this.module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Task module(Module module) {
        this.setModule(module);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", deadline='" + getDeadline() + "'" +
            "}";
    }
}
