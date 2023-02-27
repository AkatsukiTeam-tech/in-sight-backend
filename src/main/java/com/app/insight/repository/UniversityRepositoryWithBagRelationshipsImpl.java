package com.app.insight.repository;

import com.app.insight.domain.University;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class UniversityRepositoryWithBagRelationshipsImpl implements UniversityRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<University> fetchBagRelationships(Optional<University> university) {
        return university.map(this::fetchSpecializations);
    }

    @Override
    public Page<University> fetchBagRelationships(Page<University> universities) {
        return new PageImpl<>(
            fetchBagRelationships(universities.getContent()),
            universities.getPageable(),
            universities.getTotalElements()
        );
    }

    @Override
    public List<University> fetchBagRelationships(List<University> universities) {
        return Optional.of(universities).map(this::fetchSpecializations).orElse(Collections.emptyList());
    }

    University fetchSpecializations(University result) {
        return entityManager
            .createQuery(
                "select university from University university left join fetch university.specializations where university is :university",
                University.class
            )
            .setParameter("university", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<University> fetchSpecializations(List<University> universities) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, universities.size()).forEach(index -> order.put(universities.get(index).getId(), index));
        List<University> result = entityManager
            .createQuery(
                "select distinct university from University university left join fetch university.specializations where university in :universities",
                University.class
            )
            .setParameter("universities", universities)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
