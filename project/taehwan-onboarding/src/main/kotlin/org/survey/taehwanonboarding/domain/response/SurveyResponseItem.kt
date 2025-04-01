package org.survey.taehwanonboarding.domain.response

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.survey.taehwanonboarding.domain.survey.SurveyItem

@Entity
@Table(name = "response_item")
class SurveyResponseItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_response_id")
    var response: SurveyResponse? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_item_id", nullable = false)
    val surveyItem: SurveyItem,

    // todo: 단일 값 또는 쉼표로 구분된 다중 값으로 우선 고려
    @Column
    var value: String? = null,
) {
    fun isValid(): Boolean = surveyItem.validateResponse(value)
}