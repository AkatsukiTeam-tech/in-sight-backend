package com.app.insight.service.mapper;

import com.app.insight.domain.TokenBlackList;
import com.app.insight.service.dto.TokenBlackListDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TokenBlackList} and its DTO {@link TokenBlackListDTO}.
 */
@Mapper(componentModel = "spring")
public interface TokenBlackListMapper extends EntityMapper<TokenBlackListDTO, TokenBlackList> {}
