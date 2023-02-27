package com.app.insight.domain;

import com.app.insight.domain.enumeration.MediaFilesTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MediaFiles.
 */
@Entity
@Table(name = "media_files")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MediaFiles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MediaFilesTypeEnum type;

    @Column(name = "name")
    private String name;

    @Column(name = "extensions")
    private String extensions;

    @Column(name = "location")
    private String location;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "mediaFiles", "appUser", "task" }, allowSetters = true)
    private TaskAnswer taskAnswer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MediaFiles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MediaFilesTypeEnum getType() {
        return this.type;
    }

    public MediaFiles type(MediaFilesTypeEnum type) {
        this.setType(type);
        return this;
    }

    public void setType(MediaFilesTypeEnum type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public MediaFiles name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtensions() {
        return this.extensions;
    }

    public MediaFiles extensions(String extensions) {
        this.setExtensions(extensions);
        return this;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public String getLocation() {
        return this.location;
    }

    public MediaFiles location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public MediaFiles createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public AppUser getAppUser() {
        return this.appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public MediaFiles appUser(AppUser appUser) {
        this.setAppUser(appUser);
        return this;
    }

    public Task getTask() {
        return this.task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public MediaFiles task(Task task) {
        this.setTask(task);
        return this;
    }

    public TaskAnswer getTaskAnswer() {
        return this.taskAnswer;
    }

    public void setTaskAnswer(TaskAnswer taskAnswer) {
        this.taskAnswer = taskAnswer;
    }

    public MediaFiles taskAnswer(TaskAnswer taskAnswer) {
        this.setTaskAnswer(taskAnswer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MediaFiles)) {
            return false;
        }
        return id != null && id.equals(((MediaFiles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MediaFiles{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", extensions='" + getExtensions() + "'" +
            ", location='" + getLocation() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
