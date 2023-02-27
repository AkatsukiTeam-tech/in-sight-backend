package com.app.insight.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CoinsUserHistory.
 */
@Entity
@Table(name = "coins_user_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CoinsUserHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "coins")
    private Integer coins;

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

    public CoinsUserHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCoins() {
        return this.coins;
    }

    public CoinsUserHistory coins(Integer coins) {
        this.setCoins(coins);
        return this;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public AppUser getAppUser() {
        return this.appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public CoinsUserHistory appUser(AppUser appUser) {
        this.setAppUser(appUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoinsUserHistory)) {
            return false;
        }
        return id != null && id.equals(((CoinsUserHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CoinsUserHistory{" +
            "id=" + getId() +
            ", coins=" + getCoins() +
            "}";
    }
}
