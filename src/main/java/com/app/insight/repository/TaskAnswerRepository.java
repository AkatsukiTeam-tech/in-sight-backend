package com.app.insight.repository;

import com.app.insight.domain.TaskAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TaskAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskAnswerRepository extends JpaRepository<TaskAnswer, Long> {}
