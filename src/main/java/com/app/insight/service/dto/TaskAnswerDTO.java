package com.app.insight.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.app.insight.domain.TaskAnswer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaskAnswerDTO implements Serializable {

    private Long id;

    private String description;

    private Double score;

    private AppUserDTO appUser;

    private TaskDTO task;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskAnswerDTO)) {
            return false;
        }

        TaskAnswerDTO taskAnswerDTO = (TaskAnswerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taskAnswerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskAnswerDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", score=" + getScore() +
            ", appUser=" + getAppUser() +
            ", task=" + getTask() +
            "}";
    }
}
