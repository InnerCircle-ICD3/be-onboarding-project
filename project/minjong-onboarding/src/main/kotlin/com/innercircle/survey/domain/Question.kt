// src/main/kotlin/com/innercircle/survey/domain/Question.kt
package com.innercircle.survey.domain

import javax.persistence.*

@Entity
@Table(name = "questions")
class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var questionName: String,

    @Column
    var questionDescription: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var questionType: QuestionType,

    @Column(nullable = false)
    var required: Boolean = false,

    @Column(nullable = false)
    var deleted: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    var survey: Survey? = null,

    @OneToMany(mappedBy = "question", cascade = [CascadeType.ALL], orphanRemoval = true)
    val options: MutableList<Option> = mutableListOf(),

    @Column(nullable = false)
    var position: Int = 0
) {
    // 옵션 추가 메서드
    fun addOption(option: Option) {
        options.add(option)
        option.question = this
    }
}