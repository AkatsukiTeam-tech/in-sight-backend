package com.app.insight.repository;

import com.app.insight.domain.CoinsUserHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CoinsUserHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoinsUserHistoryRepository extends JpaRepository<CoinsUserHistory, Long> {}
