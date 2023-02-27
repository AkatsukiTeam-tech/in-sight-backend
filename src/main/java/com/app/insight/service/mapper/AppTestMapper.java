package com.app.insight.service.mapper;

import com.app.insight.domain.AppTest;
import com.app.insight.domain.Module;
import com.app.insight.service.dto.AppTestDTO;
import com.app.insight.service.dto.ModuleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppTest} and its DTO {@link AppTestDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppTestMapper extends EntityMapper<AppTestDTO, AppTest> {
    @Mapping(target = "module", source = "module", qualifiedByName = "moduleId")
    AppTestDTO toDto(AppTest s);

    @Named("moduleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModuleDTO toDtoModuleId(Module module);
}
