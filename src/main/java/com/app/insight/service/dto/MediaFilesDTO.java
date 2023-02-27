package com.app.insight.service.dto;

import com.app.insight.domain.enumeration.MediaFilesTypeEnum;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.app.insight.domain.MediaFiles} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MediaFilesDTO implements Serializable {

    private UUID id;

    private MediaFilesTypeEnum type;

    private String name;

    private String extensions;

    private String location;

    private ZonedDateTime createdDate;

    private AppUserDTO appUser;

    private TaskDTO task;

    private TaskAnswerDTO taskAnswer;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public MediaFilesTypeEnum getType() {
        return type;
    }

    public void setType(MediaFilesTypeEnum type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public AppUserDTO getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserDTO appUser) {
        this.appUser = appUser;
    }

    public TaskDTO getTask() {
        return task;
    }

    public void setTask(TaskDTO task) {
        this.task = task;
    }

    public TaskAnswerDTO getTaskAnswer() {
        return taskAnswer;
    }

    public void setTaskAnswer(TaskAnswerDTO taskAnswer) {
        this.taskAnswer = taskAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MediaFilesDTO)) {
            return false;
        }

        MediaFilesDTO mediaFilesDTO = (MediaFilesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mediaFilesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MediaFilesDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", extensions='" + getExtensions() + "'" +
            ", location='" + getLocation() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", appUser=" + getAppUser() +
            ", task=" + getTask() +
            ", taskAnswer=" + getTaskAnswer() +
            "}";
    }
}
