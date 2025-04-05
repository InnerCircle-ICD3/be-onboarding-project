package com.innercircle.domain.survey.repository

import com.innercircle.survey.entity.Survey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface SurveyRepository : JpaRepository<Survey, Long> {

    @Query(
        "SELECT s FROM Survey s " +
                "JOIN FETCH s.questions q " +
                "WHERE s.externalId = :id"
    )
    fun fetchSurveyQuestions(id: UUID): Optional<Survey>

    @Query(
        "SELECT s FROM Survey s " +
                "LEFT JOIN FETCH s.answers a " +
                "WHERE s.externalId = :id"
    )
    fun fetchSurveyAnswers(id: UUID): Optional<Survey>

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
        "UPDATE Survey s " +
                "SET s.participantCount = s.participantCount + 1 " +
                "WHERE s.externalId = :surveyId " +
                "AND s.participantCount <= s.participantCapacity"
    )
    fun increaseParticipantCount(surveyId: UUID): Int

}