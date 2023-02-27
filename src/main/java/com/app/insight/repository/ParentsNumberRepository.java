package com.app.insight.repository;

import com.app.insight.domain.ParentsNumber;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ParentsNumber entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParentsNumberRepository extends JpaRepository<ParentsNumber, Long> {}
