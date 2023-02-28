package com.app.insight.service.mapper;

import com.app.insight.domain.AppRole;
import com.app.insight.domain.AppUser;
import com.app.insight.domain.City;
import com.app.insight.domain.Region;
import com.app.insight.domain.School;
import com.app.insight.domain.Subgroup;
import com.app.insight.service.dto.AppRoleDTO;
import com.app.insight.service.dto.AppUserDTO;
import com.app.insight.service.dto.CityDTO;
import com.app.insight.service.dto.RegionDTO;
import com.app.insight.service.dto.SchoolDTO;
import com.app.insight.service.dto.SubgroupDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
    @Mapping(target = "appRoles", source = "appRoles")
    @Mapping(target = "subgroups", source = "subgroups")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "region", source = "region")
    @Mapping(target = "school", source = "school")
    AppUserDTO toDto(AppUser s);

    @Mapping(target = "removeAppRole", ignore = true)
    @Mapping(target = "removeSubgroup", ignore = true)
    AppUser toEntity(AppUserDTO appUserDTO);

    @Named("appRoleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppRoleDTO toDtoAppRoleId(AppRole appRole);

    @Named("appRoleIdSet")
    default Set<AppRoleDTO> toDtoAppRoleIdSet(Set<AppRole> appRole) {
        return appRole.stream().map(this::toDtoAppRoleId).collect(Collectors.toSet());
    }

    @Named("subgroupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubgroupDTO toDtoSubgroupId(Subgroup subgroup);

    @Named("subgroupIdSet")
    default Set<SubgroupDTO> toDtoSubgroupIdSet(Set<Subgroup> subgroup) {
        return subgroup.stream().map(this::toDtoSubgroupId).collect(Collectors.toSet());
    }

    @Named("cityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CityDTO toDtoCityId(City city);

    @Named("regionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RegionDTO toDtoRegionId(Region region);

    @Named("schoolId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SchoolDTO toDtoSchoolId(School school);
}
