package com.app.insight.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.app.insight.domain.CommentAnswer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommentAnswerDTO implements Serializable {

    private Long id;

    private String text;

    private ZonedDateTime dateTime;

    private CommentDTO comment;

    private AppUserDTO appUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public CommentDTO getComment() {
        return comment;
    }

    public void setComment(CommentDTO comment) {
        this.comment = comment;
    }

    public AppUserDTO getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserDTO appUser) {
        this.appUser = appUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommentAnswerDTO)) {
            return false;
        }

        CommentAnswerDTO commentAnswerDTO = (CommentAnswerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commentAnswerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentAnswerDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", dateTime='" + getDateTime() + "'" +
            ", comment=" + getComment() +
            ", appUser=" + getAppUser() +
            "}";
    }
}
