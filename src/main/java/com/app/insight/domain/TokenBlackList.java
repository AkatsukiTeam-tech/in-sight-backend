package com.app.insight.domain;

import com.app.insight.domain.enumeration.TokenTypeEnum;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TokenBlackList.
 */
@Entity
@Table(name = "token_black_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TokenBlackList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "dispose_time")
    private ZonedDateTime disposeTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TokenTypeEnum type;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TokenBlackList id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public TokenBlackList token(String token) {
        this.setToken(token);
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ZonedDateTime getDisposeTime() {
        return this.disposeTime;
    }

    public TokenBlackList disposeTime(ZonedDateTime disposeTime) {
        this.setDisposeTime(disposeTime);
        return this;
    }

    public void setDisposeTime(ZonedDateTime disposeTime) {
        this.disposeTime = disposeTime;
    }

    public TokenTypeEnum getType() {
        return this.type;
    }

    public TokenBlackList type(TokenTypeEnum type) {
        this.setType(type);
        return this;
    }

    public void setType(TokenTypeEnum type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenBlackList)) {
            return false;
        }
        return id != null && id.equals(((TokenBlackList) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TokenBlackList{" +
            "id=" + getId() +
            ", token='" + getToken() + "'" +
            ", disposeTime='" + getDisposeTime() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
