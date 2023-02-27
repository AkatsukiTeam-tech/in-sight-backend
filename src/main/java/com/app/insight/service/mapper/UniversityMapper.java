package com.app.insight.service.mapper;

import com.app.insight.domain.City;
import com.app.insight.domain.Specialization;
import com.app.insight.domain.University;
import com.app.insight.service.dto.CityDTO;
import com.app.insight.service.dto.SpecializationDTO;
import com.app.insight.service.dto.UniversityDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link University} and its DTO {@link UniversityDTO}.
 */
@Mapper(componentModel = "spring")
public interface UniversityMapper extends EntityMapper<UniversityDTO, University> {
    @Mapping(target = "specializations", source = "specializations", qualifiedByName = "specializationIdSet")
    @Mapping(target = "city", source = "city", qualifiedByName = "cityId")
    UniversityDTO toDto(University s);

    @Mapping(target = "removeSpecialization", ignore = true)
    University toEntity(UniversityDTO universityDTO);

    @Named("specializationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SpecializationDTO toDtoSpecializationId(Specialization specialization);

    @Named("specializationIdSet")
    default Set<SpecializationDTO> toDtoSpecializationIdSet(Set<Specialization> specialization) {
        return specialization.stream().map(this::toDtoSpecializationId).collect(Collectors.toSet());
    }

    @Named("cityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CityDTO toDtoCityId(City city);
}
