package com.app.insight.repository;

import com.app.insight.domain.University;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface UniversityRepositoryWithBagRelationships {
    Optional<University> fetchBagRelationships(Optional<University> university);

    List<University> fetchBagRelationships(List<University> universities);

    Page<University> fetchBagRelationships(Page<University> universities);
}
