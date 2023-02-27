package com.app.insight.repository;

import com.app.insight.domain.AppTest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppTest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppTestRepository extends JpaRepository<AppTest, Long> {}
