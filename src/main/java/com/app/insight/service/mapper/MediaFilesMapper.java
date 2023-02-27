package com.app.insight.service.mapper;

import com.app.insight.domain.AppUser;
import com.app.insight.domain.MediaFiles;
import com.app.insight.domain.Task;
import com.app.insight.domain.TaskAnswer;
import com.app.insight.service.dto.AppUserDTO;
import com.app.insight.service.dto.MediaFilesDTO;
import com.app.insight.service.dto.TaskAnswerDTO;
import com.app.insight.service.dto.TaskDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MediaFiles} and its DTO {@link MediaFilesDTO}.
 */
@Mapper(componentModel = "spring")
public interface MediaFilesMapper extends EntityMapper<MediaFilesDTO, MediaFiles> {
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    @Mapping(target = "task", source = "task", qualifiedByName = "taskId")
    @Mapping(target = "taskAnswer", source = "taskAnswer", qualifiedByName = "taskAnswerId")
    MediaFilesDTO toDto(MediaFiles s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);

    @Named("taskId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TaskDTO toDtoTaskId(Task task);

    @Named("taskAnswerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TaskAnswerDTO toDtoTaskAnswerId(TaskAnswer taskAnswer);
}
