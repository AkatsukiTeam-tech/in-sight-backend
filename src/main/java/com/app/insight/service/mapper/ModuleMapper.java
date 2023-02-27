package com.app.insight.service.mapper;

import com.app.insight.domain.Module;
import com.app.insight.domain.Subject;
import com.app.insight.service.dto.ModuleDTO;
import com.app.insight.service.dto.SubjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Module} and its DTO {@link ModuleDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModuleMapper extends EntityMapper<ModuleDTO, Module> {
    @Mapping(target = "subject", source = "subject", qualifiedByName = "subjectId")
    ModuleDTO toDto(Module s);

    @Named("subjectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubjectDTO toDtoSubjectId(Subject subject);
}
