package com.app.insight.service.mapper;

import com.app.insight.domain.Group;
import com.app.insight.domain.Question;
import com.app.insight.domain.Subject;
import com.app.insight.service.dto.GroupDTO;
import com.app.insight.service.dto.QuestionDTO;
import com.app.insight.service.dto.SubjectDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Subject} and its DTO {@link SubjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubjectMapper extends EntityMapper<SubjectDTO, Subject> {
    @Mapping(target = "groups", source = "groups", qualifiedByName = "groupIdSet")
    @Mapping(target = "question", source = "question", qualifiedByName = "questionId")
    SubjectDTO toDto(Subject s);

    @Mapping(target = "removeGroup", ignore = true)
    Subject toEntity(SubjectDTO subjectDTO);

    @Named("groupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GroupDTO toDtoGroupId(Group group);

    @Named("groupIdSet")
    default Set<GroupDTO> toDtoGroupIdSet(Set<Group> group) {
        return group.stream().map(this::toDtoGroupId).collect(Collectors.toSet());
    }

    @Named("questionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QuestionDTO toDtoQuestionId(Question question);
}
