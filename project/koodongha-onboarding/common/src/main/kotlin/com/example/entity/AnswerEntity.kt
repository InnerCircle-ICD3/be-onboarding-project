package com.example.entity

import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "answer_type")
abstract class SurveyAnswerBase(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: Survey,

    @ManyToOne
    @JoinColumn(name = "survey_item_id")
    val item: SurveyItemBase
) {
    abstract fun getAnswerValues(): List<String>
}

@Entity
@DiscriminatorValue("TEXT")
class TextAnswer(
    val content: String,
    survey: Survey,
    item: SurveyItemBase
) : SurveyAnswerBase(survey = survey, item = item) {

    override fun getAnswerValues(): List<String> = listOf(content)
}

@Entity
@DiscriminatorValue("CHOICE")
class ChoiceAnswer(
    @ManyToMany
    @JoinTable(
        name = "survey_answer_option",
        joinColumns = [JoinColumn(name = "answer_id")],
        inverseJoinColumns = [JoinColumn(name = "option_id")]
    )
    val selectedOptions: MutableList<SelectionOption> = mutableListOf(),

    survey: Survey,
    item: SurveyItemBase
) : SurveyAnswerBase(survey = survey, item = item) {

    override fun getAnswerValues(): List<String> {
        return selectedOptions.map { it.value }
    }
}
