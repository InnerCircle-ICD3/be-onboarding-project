package org.innercircle.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.innercircle.entity.SurveyItem;
import org.springframework.stereotype.Repository;

@Repository
public class SurveyItemJpaRepository {

    @PersistenceContext
    EntityManager entityManager;

    public void save(SurveyItem surveyItem) {
        entityManager.persist(surveyItem);
    }


    public SurveyItem findOne(Long seq) {
        SurveyItem surveyItem = entityManager.find(SurveyItem.class, seq);
        return surveyItem;
    }

}
