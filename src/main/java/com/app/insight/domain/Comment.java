package com.app.insight.domain;

import com.app.insight.domain.enumeration.CommentTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CommentTypeEnum type;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;

    @OneToMany(mappedBy = "comment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comment", "appUser" }, allowSetters = true)
    private Set<CommentAnswer> commentAnswers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "views", "comments" }, allowSetters = true)
    private Post post;

    @ManyToOne
    @JsonIgnoreProperties(value = { "comments", "specializations", "city" }, allowSetters = true)
    private University university;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Comment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public Comment text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CommentTypeEnum getType() {
        return this.type;
    }

    public Comment type(CommentTypeEnum type) {
        this.setType(type);
        return this;
    }

    public void setType(CommentTypeEnum type) {
        this.type = type;
    }

    public ZonedDateTime getDateTime() {
        return this.dateTime;
    }

    public Comment dateTime(ZonedDateTime dateTime) {
        this.setDateTime(dateTime);
        return this;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Set<CommentAnswer> getCommentAnswers() {
        return this.commentAnswers;
    }

    public void setCommentAnswers(Set<CommentAnswer> commentAnswers) {
        if (this.commentAnswers != null) {
            this.commentAnswers.forEach(i -> i.setComment(null));
        }
        if (commentAnswers != null) {
            commentAnswers.forEach(i -> i.setComment(this));
        }
        this.commentAnswers = commentAnswers;
    }

    public Comment commentAnswers(Set<CommentAnswer> commentAnswers) {
        this.setCommentAnswers(commentAnswers);
        return this;
    }

    public Comment addCommentAnswer(CommentAnswer commentAnswer) {
        this.commentAnswers.add(commentAnswer);
        commentAnswer.setComment(this);
        return this;
    }

    public Comment removeCommentAnswer(CommentAnswer commentAnswer) {
        this.commentAnswers.remove(commentAnswer);
        commentAnswer.setComment(null);
        return this;
    }

    public Post getPost() {
        return this.post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment post(Post post) {
        this.setPost(post);
        return this;
    }

    public University getUniversity() {
        return this.university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public Comment university(University university) {
        this.setUniversity(university);
        return this;
    }

    public AppUser getAppUser() {
        return this.appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Comment appUser(AppUser appUser) {
        this.setAppUser(appUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return id != null && id.equals(((Comment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", type='" + getType() + "'" +
            ", dateTime='" + getDateTime() + "'" +
            "}";
    }
}
