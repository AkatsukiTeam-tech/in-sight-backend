package com.app.insight.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaskAnswer.
 */
@Entity
@Table(name = "task_answer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaskAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_task_answer")
    @SequenceGenerator(name = "s_task_answer", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "score")
    private Double score;

    @OneToMany(mappedBy = "taskAnswer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "appUser", "task", "taskAnswer" }, allowSetters = true)
    private Set<MediaFiles> mediaFiles = new HashSet<>();

    @ManyToOne
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
    private AppUser appUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mediaFiles", "taskAnswers", "module" }, allowSetters = true)
    private Task task;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TaskAnswer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public TaskAnswer description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getScore() {
        return this.score;
    }

    public TaskAnswer score(Double score) {
        this.setScore(score);
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Set<MediaFiles> getMediaFiles() {
        return this.mediaFiles;
    }

    public void setMediaFiles(Set<MediaFiles> mediaFiles) {
        if (this.mediaFiles != null) {
            this.mediaFiles.forEach(i -> i.setTaskAnswer(null));
        }
        if (mediaFiles != null) {
            mediaFiles.forEach(i -> i.setTaskAnswer(this));
        }
        this.mediaFiles = mediaFiles;
    }

    public TaskAnswer mediaFiles(Set<MediaFiles> mediaFiles) {
        this.setMediaFiles(mediaFiles);
        return this;
    }

    public TaskAnswer addMediaFiles(MediaFiles mediaFiles) {
        this.mediaFiles.add(mediaFiles);
        mediaFiles.setTaskAnswer(this);
        return this;
    }

    public TaskAnswer removeMediaFiles(MediaFiles mediaFiles) {
        this.mediaFiles.remove(mediaFiles);
        mediaFiles.setTaskAnswer(null);
        return this;
    }

    public AppUser getAppUser() {
        return this.appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public TaskAnswer appUser(AppUser appUser) {
        this.setAppUser(appUser);
        return this;
    }

    public Task getTask() {
        return this.task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TaskAnswer task(Task task) {
        this.setTask(task);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskAnswer)) {
            return false;
        }
        return id != null && id.equals(((TaskAnswer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskAnswer{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", score=" + getScore() +
            "}";
    }
}
