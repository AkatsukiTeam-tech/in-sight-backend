package com.app.insight.service.mapper;

import com.app.insight.domain.AppTest;
import com.app.insight.domain.Question;
import com.app.insight.service.dto.AppTestDTO;
import com.app.insight.service.dto.QuestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Question} and its DTO {@link QuestionDTO}.
 */
@Mapper(componentModel = "spring")
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {
    @Mapping(target = "appTest", source = "appTest", qualifiedByName = "appTestId")
    QuestionDTO toDto(Question s);

    @Named("appTestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppTestDTO toDtoAppTestId(AppTest appTest);
}
