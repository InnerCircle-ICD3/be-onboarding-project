package com.example.entity

import jakarta.persistence.*

@Entity
class Survey(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    val title: String,
    val description: String? = null,

    @OneToMany(mappedBy = "survey", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<SurveyItemBase> = mutableListOf()
)

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "item_type")
abstract class SurveyItemBase(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    val name: String,
    val description: String? = null,
    val isRequired: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: Survey,

    @OneToMany(mappedBy = "item")
    val answers: MutableList<SurveyAnswerBase> = mutableListOf()
)

@Entity
@DiscriminatorValue("TEXT")
class TextItem(
    val isLong: Boolean = false,
    survey: Survey,
    name: String,
    description: String?,
    isRequired: Boolean
) : SurveyItemBase(name = name, description = description, isRequired = isRequired, survey = survey)

@Entity
@DiscriminatorValue("CHOICE")
class ChoiceItem(
    val isMultiple: Boolean = false,

    @OneToMany(mappedBy = "item", cascade = [CascadeType.ALL], orphanRemoval = true)
    val options: MutableList<SelectionOption> = mutableListOf(),

    survey: Survey,
    name: String,
    description: String?,
    isRequired: Boolean
) : SurveyItemBase(name = name, description = description, isRequired = isRequired, survey = survey)

@Entity
class SelectionOption(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    val value: String,

    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: ChoiceItem
)
