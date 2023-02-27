package com.app.insight.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.app.insight.domain.CoinsUserHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CoinsUserHistoryDTO implements Serializable {

    private Long id;

    private Integer coins;

    private AppUserDTO appUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
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
        if (!(o instanceof CoinsUserHistoryDTO)) {
            return false;
        }

        CoinsUserHistoryDTO coinsUserHistoryDTO = (CoinsUserHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, coinsUserHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CoinsUserHistoryDTO{" +
            "id=" + getId() +
            ", coins=" + getCoins() +
            ", appUser=" + getAppUser() +
            "}";
    }
}
