package com.app.insight.service.mapper;

import com.app.insight.domain.AppUser;
import com.app.insight.domain.Post;
import com.app.insight.domain.View;
import com.app.insight.service.dto.AppUserDTO;
import com.app.insight.service.dto.PostDTO;
import com.app.insight.service.dto.ViewDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link View} and its DTO {@link ViewDTO}.
 */
@Mapper(componentModel = "spring")
public interface ViewMapper extends EntityMapper<ViewDTO, View> {
    @Mapping(target = "post", source = "post", qualifiedByName = "postId")
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    ViewDTO toDto(View s);

    @Named("postId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostDTO toDtoPostId(Post post);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
