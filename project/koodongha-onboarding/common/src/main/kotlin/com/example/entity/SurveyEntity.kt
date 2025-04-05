package com.example.entity

import jakarta.persistence.*

@Entity
class Survey(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var title: String,
    var description: String? = null,

    @OneToMany(mappedBy = "survey", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<SurveyItemBase> = mutableListOf()
)

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "item_type")
abstract class SurveyItemBase(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String,
    var description: String? = null,
    var isRequired: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: Survey,

    @OneToMany(mappedBy = "item", cascade = [CascadeType.ALL], orphanRemoval = true)
    open val answers: MutableList<SurveyAnswerBase> = mutableListOf(),

    @OneToMany(mappedBy = "item", cascade = [CascadeType.ALL], orphanRemoval = true)
    open val options: MutableList<SelectionOption>? = null
)

@Entity
@DiscriminatorValue("TEXT")
class TextItem(
    var isLong: Boolean = false,
    survey: Survey,
    name: String,
    description: String?,
    isRequired: Boolean
) : SurveyItemBase(
    name = name,
    description = description,
    isRequired = isRequired,
    survey = survey,
    options = null
)

@Entity
@DiscriminatorValue("CHOICE")
class ChoiceItem(
    var isMultiple: Boolean = false,
    survey: Survey,
    name: String,
    description: String?,
    isRequired: Boolean,

    override val options: MutableList<SelectionOption> = mutableListOf()
) : SurveyItemBase(
    name = name,
    description = description,
    isRequired = isRequired,
    survey = survey,
    options = options
)

@Entity
class SelectionOption(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    val value: String,

    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: SurveyItemBase
)

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "answer_type")
abstract class SurveyAnswerBase(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    val questionName: String,
    val questionType: String,

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
    survey: Survey,
    item: SurveyItemBase
) : SurveyAnswerBase(
    questionName = questionName,
    questionType = "TEXT",
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
    survey: Survey,
    item: SurveyItemBase
) : SurveyAnswerBase(
    questionName = questionName,
    questionType = "CHOICE",
    survey = survey,
    item = item
) {
    override fun getAnswerValues(): List<String> = selectedValues
}
