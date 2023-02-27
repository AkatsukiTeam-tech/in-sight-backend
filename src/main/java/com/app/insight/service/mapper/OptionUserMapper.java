package com.app.insight.service.mapper;

import com.app.insight.domain.AppUser;
import com.app.insight.domain.OptionUser;
import com.app.insight.service.dto.AppUserDTO;
import com.app.insight.service.dto.OptionUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OptionUser} and its DTO {@link OptionUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface OptionUserMapper extends EntityMapper<OptionUserDTO, OptionUser> {
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    OptionUserDTO toDto(OptionUser s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
