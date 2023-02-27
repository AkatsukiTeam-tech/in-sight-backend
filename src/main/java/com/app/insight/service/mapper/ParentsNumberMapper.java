package com.app.insight.service.mapper;

import com.app.insight.domain.AppUser;
import com.app.insight.domain.ParentsNumber;
import com.app.insight.service.dto.AppUserDTO;
import com.app.insight.service.dto.ParentsNumberDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParentsNumber} and its DTO {@link ParentsNumberDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParentsNumberMapper extends EntityMapper<ParentsNumberDTO, ParentsNumber> {
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    ParentsNumberDTO toDto(ParentsNumber s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
