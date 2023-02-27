package com.app.insight.service.mapper;

import com.app.insight.domain.Group;
import com.app.insight.domain.Subgroup;
import com.app.insight.service.dto.GroupDTO;
import com.app.insight.service.dto.SubgroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Subgroup} and its DTO {@link SubgroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubgroupMapper extends EntityMapper<SubgroupDTO, Subgroup> {
    @Mapping(target = "group", source = "group", qualifiedByName = "groupId")
    SubgroupDTO toDto(Subgroup s);

    @Named("groupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GroupDTO toDtoGroupId(Group group);
}
