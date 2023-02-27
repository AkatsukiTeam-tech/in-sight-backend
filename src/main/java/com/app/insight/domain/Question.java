package com.app.insight.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_question")
    @SequenceGenerator(name = "s_question", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "question", nullable = false, unique = true)
    private String question;

    @OneToMany(mappedBy = "question")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "modules", "groups", "question" }, allowSetters = true)
    private Set<Subject> subjects = new HashSet<>();

    @OneToMany(mappedBy = "question")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "question" }, allowSetters = true)
    private Set<Option> options = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "questions", "module" }, allowSetters = true)
    private AppTest appTest;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Question id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return this.question;
    }

    public Question question(String question) {
        this.setQuestion(question);
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<Subject> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        if (this.subjects != null) {
            this.subjects.forEach(i -> i.setQuestion(null));
        }
        if (subjects != null) {
            subjects.forEach(i -> i.setQuestion(this));
        }
        this.subjects = subjects;
    }

    public Question subjects(Set<Subject> subjects) {
        this.setSubjects(subjects);
        return this;
    }

    public Question addSubject(Subject subject) {
        this.subjects.add(subject);
        subject.setQuestion(this);
        return this;
    }

    public Question removeSubject(Subject subject) {
        this.subjects.remove(subject);
        subject.setQuestion(null);
        return this;
    }

    public Set<Option> getOptions() {
        return this.options;
    }

    public void setOptions(Set<Option> options) {
        if (this.options != null) {
            this.options.forEach(i -> i.setQuestion(null));
        }
        if (options != null) {
            options.forEach(i -> i.setQuestion(this));
        }
        this.options = options;
    }

    public Question options(Set<Option> options) {
        this.setOptions(options);
        return this;
    }

    public Question addOption(Option option) {
        this.options.add(option);
        option.setQuestion(this);
        return this;
    }

    public Question removeOption(Option option) {
        this.options.remove(option);
        option.setQuestion(null);
        return this;
    }

    public AppTest getAppTest() {
        return this.appTest;
    }

    public void setAppTest(AppTest appTest) {
        this.appTest = appTest;
    }

    public Question appTest(AppTest appTest) {
        this.setAppTest(appTest);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        return id != null && id.equals(((Question) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            "}";
    }
}
