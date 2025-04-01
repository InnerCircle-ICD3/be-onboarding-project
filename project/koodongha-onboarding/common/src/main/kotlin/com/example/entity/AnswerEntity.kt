package com.example.entity

import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "answer_type")
abstract class SurveyAnswerBase(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    val questionName: String,
    val questionType: String, // TEXT or CHOICE

    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: Survey,

    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: SurveyItemBase
) {
    abstract fun getAnswerValues(): List<String>
}

@Entity
@DiscriminatorValue("TEXT")
class TextAnswer(
    val content: String,
    questionName: String,
    questionType: String = "TEXT",
    survey: Survey,
    item: SurveyItemBase
) : SurveyAnswerBase(
    questionName = questionName,
    questionType = questionType,
    survey = survey,
    item = item
) {
    override fun getAnswerValues(): List<String> = listOf(content)
}

@Entity
@DiscriminatorValue("CHOICE")
class ChoiceAnswer(
    @ElementCollection
    val selectedValues: List<String>,
    questionName: String,
    questionType: String = "CHOICE",
    survey: Survey,
    item: SurveyItemBase
) : SurveyAnswerBase(
    questionName = questionName,
    questionType = questionType,
    survey = survey,
    item = item
) {
    override fun getAnswerValues(): List<String> = selectedValues
}
