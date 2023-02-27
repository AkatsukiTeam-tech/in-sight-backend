package com.app.insight.service.mapper;

import com.app.insight.domain.Module;
import com.app.insight.domain.Task;
import com.app.insight.service.dto.ModuleDTO;
import com.app.insight.service.dto.TaskDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring")
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {
    @Mapping(target = "module", source = "module", qualifiedByName = "moduleId")
    TaskDTO toDto(Task s);

    @Named("moduleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModuleDTO toDtoModuleId(Module module);
}
