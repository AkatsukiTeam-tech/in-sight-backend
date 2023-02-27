package com.app.insight.repository;

import com.app.insight.domain.Specialization;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Specialization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {}
