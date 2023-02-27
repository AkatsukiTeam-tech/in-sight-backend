package com.app.insight.service.mapper;

import com.app.insight.domain.AppUser;
import com.app.insight.domain.Comment;
import com.app.insight.domain.CommentAnswer;
import com.app.insight.service.dto.AppUserDTO;
import com.app.insight.service.dto.CommentAnswerDTO;
import com.app.insight.service.dto.CommentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommentAnswer} and its DTO {@link CommentAnswerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentAnswerMapper extends EntityMapper<CommentAnswerDTO, CommentAnswer> {
    @Mapping(target = "comment", source = "comment", qualifiedByName = "commentId")
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    CommentAnswerDTO toDto(CommentAnswer s);

    @Named("commentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommentDTO toDtoCommentId(Comment comment);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
