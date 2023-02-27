package com.app.insight.repository;

import com.app.insight.domain.CommentAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CommentAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentAnswerRepository extends JpaRepository<CommentAnswer, Long> {}
