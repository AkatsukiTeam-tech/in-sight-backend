package com.app.insight.service.mapper;

import com.app.insight.domain.AppUser;
import com.app.insight.domain.CoinsUserHistory;
import com.app.insight.service.dto.AppUserDTO;
import com.app.insight.service.dto.CoinsUserHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CoinsUserHistory} and its DTO {@link CoinsUserHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CoinsUserHistoryMapper extends EntityMapper<CoinsUserHistoryDTO, CoinsUserHistory> {
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    CoinsUserHistoryDTO toDto(CoinsUserHistory s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
