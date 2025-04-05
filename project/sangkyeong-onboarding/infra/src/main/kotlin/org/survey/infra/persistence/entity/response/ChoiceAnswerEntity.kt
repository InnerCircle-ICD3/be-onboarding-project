package org.survey.infra.persistence.entity.response

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table

@Entity
@Table(name = "choice_answer")
class ChoiceAnswerEntity(
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    val id: Long = 0,
    val surveyResponseId: Long,
    val surveyItemId: Long,
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "choice_answer_option",
        joinColumns = [JoinColumn(name = "choice_answer_id")],
    )
    @Column(name = "item_option_ids")
    val itemOptionIds: MutableSet<Long> = mutableSetOf(),
)
