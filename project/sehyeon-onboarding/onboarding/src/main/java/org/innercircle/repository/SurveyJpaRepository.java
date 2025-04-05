package org.innercircle.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.innercircle.entity.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
// try-catch 블록 깔고, transaction 열고, 로직 돌리고, 예외 나면 rollback 해주고, 이거를 AOP로 자동화 해준 게 @Transactional 어노테이션이잖아
// @Transactional 어노테이션 없으면 네가 지금 트랜잭션을 어디에서 열고 있어. 어디긴 어디야 안 열고 있지.
// JPA 는 PersistenceContext 생명 주기가 보통 transaction 과 동일한데 트랜잭션이 없으니 JPA 도 못 열게 됨. 아래처럼 에러 남.
// No EntityManager with actual transaction available for current thread - cannot reliably process 'persist' call
public class SurveyJpaRepository {

    @PersistenceContext // @Autowired 아니고 @PersistenceContext 임. 주의. DB에
    EntityManager entityManager;


    public Long save(Survey survey) {
        entityManager.persist(survey);
        return survey.getSeq();
    }


    public Survey findOne(Long seq) {
        Survey result = entityManager.find(Survey.class, seq);
        return result;
    }

}
