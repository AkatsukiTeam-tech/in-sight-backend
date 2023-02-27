package com.app.insight.repository;

import com.app.insight.domain.TokenBlackList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TokenBlackList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {}
