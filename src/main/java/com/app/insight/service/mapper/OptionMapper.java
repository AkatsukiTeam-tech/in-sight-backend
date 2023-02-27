package com.app.insight.service.mapper;

import com.app.insight.domain.Option;
import com.app.insight.domain.Question;
import com.app.insight.service.dto.OptionDTO;
import com.app.insight.service.dto.QuestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Option} and its DTO {@link OptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface OptionMapper extends EntityMapper<OptionDTO, Option> {
    @Mapping(target = "question", source = "question", qualifiedByName = "questionId")
    OptionDTO toDto(Option s);

    @Named("questionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QuestionDTO toDtoQuestionId(Question question);
}
