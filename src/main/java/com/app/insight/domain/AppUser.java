package com.app.insight.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * A AppUser.
 */
@Entity
@Table(name = "app_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUser implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_app_user")
    @SequenceGenerator(name = "s_app_user", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "reg_date_time")
    private ZonedDateTime regDateTime;

    @NotNull
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @NotNull
    @Column(name = "iin", nullable = false, unique = true)
    private String iin;

    @Column(name = "coins")
    private Integer coins;

    @Column(name = "ent_result")
    private Integer entResult;

    @OneToMany(mappedBy = "appUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "appUser" }, allowSetters = true)
    private Set<ParentsNumber> parentsNumbers = new HashSet<>();

    @OneToMany(mappedBy = "appUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "post", "appUser" }, allowSetters = true)
    private Set<View> views = new HashSet<>();

    @OneToMany(mappedBy = "appUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "commentAnswers", "post", "university", "appUser" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "appUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comment", "appUser" }, allowSetters = true)
    private Set<CommentAnswer> commentAnswers = new HashSet<>();

    @OneToMany(mappedBy = "appUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mediaFiles", "appUser", "task" }, allowSetters = true)
    private Set<TaskAnswer> taskAnswers = new HashSet<>();

    @OneToMany(mappedBy = "appUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "appUser", "task", "taskAnswer" }, allowSetters = true)
    private Set<MediaFiles> mediaFiles = new HashSet<>();

    @OneToMany(mappedBy = "appUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "appUser" }, allowSetters = true)
    private Set<OptionUser> optionUsers = new HashSet<>();

    @OneToMany(mappedBy = "appUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "appUser" }, allowSetters = true)
    private Set<CoinsUserHistory> coinsUserHistories = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "rel_app_user__app_role",
        joinColumns = @JoinColumn(name = "app_user_id"),
        inverseJoinColumns = @JoinColumn(name = "app_role_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "appUsers" }, allowSetters = true)
    private Set<AppRole> appRoles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "rel_app_user__subgroup",
        joinColumns = @JoinColumn(name = "app_user_id"),
        inverseJoinColumns = @JoinColumn(name = "subgroup_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "schedules", "group", "appUsers" }, allowSetters = true)
    private Set<Subgroup> subgroups = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "regions", "appUsers", "universities" }, allowSetters = true)
    private City city;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "appUsers", "city" }, allowSetters = true)
    private Region region;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "appUsers" }, allowSetters = true)
    private School school;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public AppUser firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public AppUser middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public AppUser lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public AppUser isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public AppUser isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getAge() {
        return this.age;
    }

    public AppUser age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return this.email;
    }

    public AppUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return this.login;
    }

    public AppUser login(String login) {
        this.setLogin(login);
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public AppUser password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ZonedDateTime getRegDateTime() {
        return this.regDateTime;
    }

    public AppUser regDateTime(ZonedDateTime regDateTime) {
        this.setRegDateTime(regDateTime);
        return this;
    }

    public void setRegDateTime(ZonedDateTime regDateTime) {
        this.regDateTime = regDateTime;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public AppUser phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIin() {
        return this.iin;
    }

    public AppUser iin(String iin) {
        this.setIin(iin);
        return this;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public Integer getCoins() {
        return this.coins;
    }

    public AppUser coins(Integer coins) {
        this.setCoins(coins);
        return this;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public Integer getEntResult() {
        return this.entResult;
    }

    public AppUser entResult(Integer entResult) {
        this.setEntResult(entResult);
        return this;
    }

    public void setEntResult(Integer entResult) {
        this.entResult = entResult;
    }

    public Set<ParentsNumber> getParentsNumbers() {
        return this.parentsNumbers;
    }

    public void setParentsNumbers(Set<ParentsNumber> parentsNumbers) {
        if (this.parentsNumbers != null) {
            this.parentsNumbers.forEach(i -> i.setAppUser(null));
        }
        if (parentsNumbers != null) {
            parentsNumbers.forEach(i -> i.setAppUser(this));
        }
        this.parentsNumbers = parentsNumbers;
    }

    public AppUser parentsNumbers(Set<ParentsNumber> parentsNumbers) {
        this.setParentsNumbers(parentsNumbers);
        return this;
    }

    public AppUser addParentsNumber(ParentsNumber parentsNumber) {
        this.parentsNumbers.add(parentsNumber);
        parentsNumber.setAppUser(this);
        return this;
    }

    public AppUser removeParentsNumber(ParentsNumber parentsNumber) {
        this.parentsNumbers.remove(parentsNumber);
        parentsNumber.setAppUser(null);
        return this;
    }

    public Set<View> getViews() {
        return this.views;
    }

    public void setViews(Set<View> views) {
        if (this.views != null) {
            this.views.forEach(i -> i.setAppUser(null));
        }
        if (views != null) {
            views.forEach(i -> i.setAppUser(this));
        }
        this.views = views;
    }

    public AppUser views(Set<View> views) {
        this.setViews(views);
        return this;
    }

    public AppUser addView(View view) {
        this.views.add(view);
        view.setAppUser(this);
        return this;
    }

    public AppUser removeView(View view) {
        this.views.remove(view);
        view.setAppUser(null);
        return this;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setAppUser(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setAppUser(this));
        }
        this.comments = comments;
    }

    public AppUser comments(Set<Comment> comments) {
        this.setComments(comments);
        return this;
    }

    public AppUser addComment(Comment comment) {
        this.comments.add(comment);
        comment.setAppUser(this);
        return this;
    }

    public AppUser removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setAppUser(null);
        return this;
    }

    public Set<CommentAnswer> getCommentAnswers() {
        return this.commentAnswers;
    }

    public void setCommentAnswers(Set<CommentAnswer> commentAnswers) {
        if (this.commentAnswers != null) {
            this.commentAnswers.forEach(i -> i.setAppUser(null));
        }
        if (commentAnswers != null) {
            commentAnswers.forEach(i -> i.setAppUser(this));
        }
        this.commentAnswers = commentAnswers;
    }

    public AppUser commentAnswers(Set<CommentAnswer> commentAnswers) {
        this.setCommentAnswers(commentAnswers);
        return this;
    }

    public AppUser addCommentAnswer(CommentAnswer commentAnswer) {
        this.commentAnswers.add(commentAnswer);
        commentAnswer.setAppUser(this);
        return this;
    }

    public AppUser removeCommentAnswer(CommentAnswer commentAnswer) {
        this.commentAnswers.remove(commentAnswer);
        commentAnswer.setAppUser(null);
        return this;
    }

    public Set<TaskAnswer> getTaskAnswers() {
        return this.taskAnswers;
    }

    public void setTaskAnswers(Set<TaskAnswer> taskAnswers) {
        if (this.taskAnswers != null) {
            this.taskAnswers.forEach(i -> i.setAppUser(null));
        }
        if (taskAnswers != null) {
            taskAnswers.forEach(i -> i.setAppUser(this));
        }
        this.taskAnswers = taskAnswers;
    }

    public AppUser taskAnswers(Set<TaskAnswer> taskAnswers) {
        this.setTaskAnswers(taskAnswers);
        return this;
    }

    public AppUser addTaskAnswer(TaskAnswer taskAnswer) {
        this.taskAnswers.add(taskAnswer);
        taskAnswer.setAppUser(this);
        return this;
    }

    public AppUser removeTaskAnswer(TaskAnswer taskAnswer) {
        this.taskAnswers.remove(taskAnswer);
        taskAnswer.setAppUser(null);
        return this;
    }

    public Set<MediaFiles> getMediaFiles() {
        return this.mediaFiles;
    }

    public void setMediaFiles(Set<MediaFiles> mediaFiles) {
        if (this.mediaFiles != null) {
            this.mediaFiles.forEach(i -> i.setAppUser(null));
        }
        if (mediaFiles != null) {
            mediaFiles.forEach(i -> i.setAppUser(this));
        }
        this.mediaFiles = mediaFiles;
    }

    public AppUser mediaFiles(Set<MediaFiles> mediaFiles) {
        this.setMediaFiles(mediaFiles);
        return this;
    }

    public AppUser addMediaFiles(MediaFiles mediaFiles) {
        this.mediaFiles.add(mediaFiles);
        mediaFiles.setAppUser(this);
        return this;
    }

    public AppUser removeMediaFiles(MediaFiles mediaFiles) {
        this.mediaFiles.remove(mediaFiles);
        mediaFiles.setAppUser(null);
        return this;
    }

    public Set<OptionUser> getOptionUsers() {
        return this.optionUsers;
    }

    public void setOptionUsers(Set<OptionUser> optionUsers) {
        if (this.optionUsers != null) {
            this.optionUsers.forEach(i -> i.setAppUser(null));
        }
        if (optionUsers != null) {
            optionUsers.forEach(i -> i.setAppUser(this));
        }
        this.optionUsers = optionUsers;
    }

    public AppUser optionUsers(Set<OptionUser> optionUsers) {
        this.setOptionUsers(optionUsers);
        return this;
    }

    public AppUser addOptionUser(OptionUser optionUser) {
        this.optionUsers.add(optionUser);
        optionUser.setAppUser(this);
        return this;
    }

    public AppUser removeOptionUser(OptionUser optionUser) {
        this.optionUsers.remove(optionUser);
        optionUser.setAppUser(null);
        return this;
    }

    public Set<CoinsUserHistory> getCoinsUserHistories() {
        return this.coinsUserHistories;
    }

    public void setCoinsUserHistories(Set<CoinsUserHistory> coinsUserHistories) {
        if (this.coinsUserHistories != null) {
            this.coinsUserHistories.forEach(i -> i.setAppUser(null));
        }
        if (coinsUserHistories != null) {
            coinsUserHistories.forEach(i -> i.setAppUser(this));
        }
        this.coinsUserHistories = coinsUserHistories;
    }

    public AppUser coinsUserHistories(Set<CoinsUserHistory> coinsUserHistories) {
        this.setCoinsUserHistories(coinsUserHistories);
        return this;
    }

    public AppUser addCoinsUserHistory(CoinsUserHistory coinsUserHistory) {
        this.coinsUserHistories.add(coinsUserHistory);
        coinsUserHistory.setAppUser(this);
        return this;
    }

    public AppUser removeCoinsUserHistory(CoinsUserHistory coinsUserHistory) {
        this.coinsUserHistories.remove(coinsUserHistory);
        coinsUserHistory.setAppUser(null);
        return this;
    }

    public Set<AppRole> getAppRoles() {
        return this.appRoles;
    }

    public void setAppRoles(Set<AppRole> appRoles) {
        this.appRoles = appRoles;
    }

    public AppUser appRoles(Set<AppRole> appRoles) {
        this.setAppRoles(appRoles);
        return this;
    }

    public AppUser addAppRole(AppRole appRole) {
        this.appRoles.add(appRole);
        appRole.getAppUsers().add(this);
        return this;
    }

    public AppUser removeAppRole(AppRole appRole) {
        this.appRoles.remove(appRole);
        appRole.getAppUsers().remove(this);
        return this;
    }

    public Set<Subgroup> getSubgroups() {
        return this.subgroups;
    }

    public void setSubgroups(Set<Subgroup> subgroups) {
        this.subgroups = subgroups;
    }

    public AppUser subgroups(Set<Subgroup> subgroups) {
        this.setSubgroups(subgroups);
        return this;
    }

    public AppUser addSubgroup(Subgroup subgroup) {
        this.subgroups.add(subgroup);
        subgroup.getAppUsers().add(this);
        return this;
    }

    public AppUser removeSubgroup(Subgroup subgroup) {
        this.subgroups.remove(subgroup);
        subgroup.getAppUsers().remove(this);
        return this;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public AppUser city(City city) {
        this.setCity(city);
        return this;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public AppUser region(Region region) {
        this.setRegion(region);
        return this;
    }

    public School getSchool() {
        return this.school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public AppUser school(School school) {
        this.setSchool(school);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUser)) {
            return false;
        }
        return id != null && id.equals(((AppUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUser{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", age=" + getAge() +
            ", email='" + getEmail() + "'" +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", regDateTime='" + getRegDateTime() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", iin='" + getIin() + "'" +
            ", coins=" + getCoins() +
            ", entResult=" + getEntResult() +
            "}";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        for (AppRole appRole : getRoles()) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(appRole.getName().name());
            grantedAuthorityList.add(simpleGrantedAuthority);
        }

        return grantedAuthorityList;
    }

    public Set<AppRole> getRoles() {
        return this.appRoles;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public boolean isManager() {
        for (GrantedAuthority grantedAuthority : getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ROLE_MANAGER")) {
                return true;
            }
        }

        return false;
    }

    public boolean isStudent() {
        for (GrantedAuthority grantedAuthority : getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ROLE_STUDENT")) {
                return true;
            }
        }

        return false;
    }

    public boolean isAdmin() {
        for (GrantedAuthority grantedAuthority : getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }

        return false;
    }

    public boolean isTeacher() {
        for (GrantedAuthority grantedAuthority : getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ROLE_TEACHER")) {
                return true;
            }
        }

        return false;
    }
}
