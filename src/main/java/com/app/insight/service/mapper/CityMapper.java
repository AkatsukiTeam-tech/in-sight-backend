package com.app.insight.service.mapper;

import com.app.insight.domain.City;
import com.app.insight.domain.Region;
import com.app.insight.service.dto.CityDTO;
import com.app.insight.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CityMapper extends EntityMapper<CityDTO, City> {
    @Mapping(target = "region", source = "region", qualifiedByName = "regionId")
    CityDTO toDto(City s);

    @Named("regionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RegionDTO toDtoRegionId(Region region);
}
