package com.app.insight.service.dto;

import com.app.insight.domain.enumeration.TokenTypeEnum;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.app.insight.domain.TokenBlackList} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TokenBlackListDTO implements Serializable {

    private Long id;

    private String token;

    private ZonedDateTime disposeTime;

    private TokenTypeEnum type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ZonedDateTime getDisposeTime() {
        return disposeTime;
    }

    public void setDisposeTime(ZonedDateTime disposeTime) {
        this.disposeTime = disposeTime;
    }

    public TokenTypeEnum getType() {
        return type;
    }

    public void setType(TokenTypeEnum type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenBlackListDTO)) {
            return false;
        }

        TokenBlackListDTO tokenBlackListDTO = (TokenBlackListDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tokenBlackListDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TokenBlackListDTO{" +
            "id=" + getId() +
            ", token='" + getToken() + "'" +
            ", disposeTime='" + getDisposeTime() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
