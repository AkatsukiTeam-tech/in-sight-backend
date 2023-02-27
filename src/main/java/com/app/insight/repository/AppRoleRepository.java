package com.app.insight.repository;

import com.app.insight.domain.AppRole;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {}
