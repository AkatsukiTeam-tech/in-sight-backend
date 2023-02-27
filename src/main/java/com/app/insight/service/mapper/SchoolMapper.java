package com.app.insight.service.mapper;

import com.app.insight.domain.School;
import com.app.insight.service.dto.SchoolDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link School} and its DTO {@link SchoolDTO}.
 */
@Mapper(componentModel = "spring")
public interface SchoolMapper extends EntityMapper<SchoolDTO, School> {}
