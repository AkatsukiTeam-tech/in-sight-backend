package com.app.insight.service.mapper;

import com.app.insight.domain.City;
import com.app.insight.domain.Region;
import com.app.insight.service.dto.CityDTO;
import com.app.insight.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Region} and its DTO {@link RegionDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {
    @Mapping(target = "city", source = "city", qualifiedByName = "cityId")
    RegionDTO toDto(Region s);

    @Named("cityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CityDTO toDtoCityId(City city);
}
