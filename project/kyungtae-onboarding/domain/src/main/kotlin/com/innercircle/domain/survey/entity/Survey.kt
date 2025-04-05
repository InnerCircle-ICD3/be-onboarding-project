package com.innercircle.survey.entity

import com.innercircle.common.DateTimeSystemAttribute
import com.innercircle.domain.survey.command.dto.SurveyCreateCommand
import com.innercircle.domain.survey.command.dto.SurveyUpdateCommand
import com.innercircle.domain.survey.entity.SurveyContext
import jakarta.persistence.*
import org.hibernate.annotations.Filter
import java.time.LocalDateTime
import java.util.*

@Entity
class Survey private constructor(

    @Embedded
    val context: SurveyContext,

    var participantCapacity: Int = 0,

    val participantCount : Int = 0,

    @Column(nullable = false)
    var startAt: LocalDateTime,

    @Column(nullable = false)
    var endAt: LocalDateTime
) : DateTimeSystemAttribute() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(unique = true, nullable = false)
    val externalId: UUID = UUID.randomUUID()

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('READY', 'IN_PROGRESS', 'END')")
    var status: SurveyStatus = SurveyStatus.READY
        protected set

    @OneToMany(mappedBy = "survey", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @Filter(name = "deletedFilter", condition = "is_deleted = :isDeleted")
    val questions: MutableList<SurveyQuestion> = mutableListOf()

    @OneToMany(mappedBy = "survey", fetch = FetchType.LAZY)
    @Filter(name = "deletedFilter", condition = "is_deleted = :isDeleted")
    val answers: MutableList<SurveyAnswer> = mutableListOf()

    @Column
    var startedAt: LocalDateTime? = null
        protected set

    @Column
    var endedAt: LocalDateTime? = null
        protected set

    init {
        require(!startAt.isAfter(endAt)) { "startAt must be before endAt" }
    }

    companion object {
        fun from(command: SurveyCreateCommand): Survey {
            val survey = Survey(
                SurveyContext(
                    name = command.name,
                    description = command.description
                ),
                startAt = command.startAt,
                endAt = command.endAt,
                participantCapacity = command.participantCapacity
            )
            command.questions.forEach { questionCommand -> SurveyQuestion.of(survey, questionCommand) }
            return survey
        }
    }

    fun start() {
        transitionTo(SurveyStatus.IN_PROGRESS)
        this.startedAt = LocalDateTime.now()
    }

    fun end() {
        transitionTo(SurveyStatus.END)
        this.endedAt = LocalDateTime.now()
    }

    fun transitionTo(newStatus: SurveyStatus) {
        check(status.canTransitionTo(newStatus)) { "Cannot transition from $status to $newStatus" }
        status = newStatus
    }

    fun update(updateCommand: SurveyUpdateCommand) {
        this.context.name = updateCommand.name
        this.context.description = updateCommand.description
        this.participantCapacity = updateCommand.participantCapacity
        this.startAt = updateCommand.startAt
        this.endAt = updateCommand.endAt

        val updatedQuestionIds = updateCommand.questions.mapNotNull { it.id }
        if (updatedQuestionIds.isEmpty()) {
            questions.forEach { it.softDelete() }
        } else {
            questions.find { it.id !in updatedQuestionIds }.also { it?.softDelete() }
        }

        updateCommand.questions.forEach { questionCommand ->
            val question = questionCommand.id
                .let { id ->
                    questions.find { it.id == id }
                }
                ?.apply {
                    questionType = questionCommand.questionType
                    context.name = questionCommand.name
                    context.description = questionCommand.description
                    required = questionCommand.required
                }
                ?: SurveyQuestion.of(this, questionCommand.toCreateCommand())
                    .also { questions.add(it) }

            val updatedOptionIds = questionCommand.options.mapNotNull { it.id }
            if (updatedOptionIds.isEmpty()) {
                question.options.forEach { it.softDelete() }
            } else {
                question.options.find { it.id !in updatedOptionIds }.also { it?.softDelete() }
            }

            questionCommand.options.forEach { optionCommand ->
                optionCommand.id
                    ?.let { id -> question.options.find { it.id == id } }
                    ?.apply {
                        content = optionCommand.content
                    }
                    ?: SurveyQuestionOption.of(question, optionCommand.toCreateCommand())
                        .also { question.options.add(it) }
            }
        }
    }
}

enum class SurveyStatus {
    READY {
        override fun canTransitionTo(target: SurveyStatus) = target == IN_PROGRESS
    },
    IN_PROGRESS {
        override fun canTransitionTo(target: SurveyStatus) = target == END
    },
    END {
        override fun canTransitionTo(target: SurveyStatus) = false
    };

    abstract fun canTransitionTo(target: SurveyStatus): Boolean
}