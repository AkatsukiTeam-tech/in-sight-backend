package com.app.insight.repository;

import com.app.insight.domain.Subject;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface SubjectRepositoryWithBagRelationships {
    Optional<Subject> fetchBagRelationships(Optional<Subject> subject);

    List<Subject> fetchBagRelationships(List<Subject> subjects);

    Page<Subject> fetchBagRelationships(Page<Subject> subjects);
}
