package com.app.insight.service.mapper;

import com.app.insight.domain.Region;
import com.app.insight.service.dto.RegionDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Region} and its DTO {@link RegionDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {
}
