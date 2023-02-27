package com.app.insight.service.mapper;

import com.app.insight.domain.AppUser;
import com.app.insight.domain.Comment;
import com.app.insight.domain.Post;
import com.app.insight.domain.University;
import com.app.insight.service.dto.AppUserDTO;
import com.app.insight.service.dto.CommentDTO;
import com.app.insight.service.dto.PostDTO;
import com.app.insight.service.dto.UniversityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "post", source = "post", qualifiedByName = "postId")
    @Mapping(target = "university", source = "university", qualifiedByName = "universityId")
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    CommentDTO toDto(Comment s);

    @Named("postId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostDTO toDtoPostId(Post post);

    @Named("universityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UniversityDTO toDtoUniversityId(University university);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
