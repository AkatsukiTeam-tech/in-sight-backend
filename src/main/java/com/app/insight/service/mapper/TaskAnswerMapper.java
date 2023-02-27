package com.app.insight.service.mapper;

import com.app.insight.domain.AppUser;
import com.app.insight.domain.Task;
import com.app.insight.domain.TaskAnswer;
import com.app.insight.service.dto.AppUserDTO;
import com.app.insight.service.dto.TaskAnswerDTO;
import com.app.insight.service.dto.TaskDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskAnswer} and its DTO {@link TaskAnswerDTO}.
 */
@Mapper(componentModel = "spring")
public interface TaskAnswerMapper extends EntityMapper<TaskAnswerDTO, TaskAnswer> {
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    @Mapping(target = "task", source = "task", qualifiedByName = "taskId")
    TaskAnswerDTO toDto(TaskAnswer s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);

    @Named("taskId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TaskDTO toDtoTaskId(Task task);
}
