package com.app.insight.service.mapper;

import com.app.insight.domain.Module;
import com.app.insight.domain.Schedule;
import com.app.insight.domain.Subgroup;
import com.app.insight.service.dto.ModuleDTO;
import com.app.insight.service.dto.ScheduleDTO;
import com.app.insight.service.dto.SubgroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Schedule} and its DTO {@link ScheduleDTO}.
 */
@Mapper(componentModel = "spring")
public interface ScheduleMapper extends EntityMapper<ScheduleDTO, Schedule> {
    @Mapping(target = "subgroup", source = "subgroup", qualifiedByName = "subgroupId")
    @Mapping(target = "module", source = "module", qualifiedByName = "moduleId")
    ScheduleDTO toDto(Schedule s);

    @Named("subgroupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubgroupDTO toDtoSubgroupId(Subgroup subgroup);

    @Named("moduleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModuleDTO toDtoModuleId(Module module);
}
