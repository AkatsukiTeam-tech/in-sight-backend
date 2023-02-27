package com.app.insight.repository;

import com.app.insight.domain.AppRole;
import com.app.insight.domain.enumeration.AppRoleTypeEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    Optional<AppRole> findByName(AppRoleTypeEnum name);
}
