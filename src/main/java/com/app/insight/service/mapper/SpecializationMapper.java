package com.app.insight.service.mapper;

import com.app.insight.domain.Specialization;
import com.app.insight.service.dto.SpecializationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Specialization} and its DTO {@link SpecializationDTO}.
 */
@Mapper(componentModel = "spring")
public interface SpecializationMapper extends EntityMapper<SpecializationDTO, Specialization> {}
