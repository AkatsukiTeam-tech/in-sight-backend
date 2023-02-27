package com.app.insight.repository;

import com.app.insight.domain.OptionUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OptionUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionUserRepository extends JpaRepository<OptionUser, Long> {}
