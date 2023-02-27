package com.app.insight.repository;

import com.app.insight.domain.Subgroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Subgroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubgroupRepository extends JpaRepository<Subgroup, Long> {}
