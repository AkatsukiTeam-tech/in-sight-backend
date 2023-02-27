package com.app.insight.service.mapper;

import com.app.insight.domain.AppRole;
import com.app.insight.service.dto.AppRoleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppRole} and its DTO {@link AppRoleDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppRoleMapper extends EntityMapper<AppRoleDTO, AppRole> {}
