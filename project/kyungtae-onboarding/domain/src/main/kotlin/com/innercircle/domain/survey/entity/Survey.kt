package com.innercircle.survey.entity

import com.innercircle.common.DateTimeSystemAttribute
import com.innercircle.domain.survey.command.dto.SurveyCreateCommand
import com.innercircle.domain.survey.entity.SurveyContext
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "survey")
class Survey private constructor(

    @Embedded
    val context: SurveyContext,

    val participantCapacity : Int = 0,

    @Column(nullable = false)
    val startAt: LocalDateTime,

    @Column(nullable = false)
    val endAt: LocalDateTime
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
    val questions: MutableList<SurveyQuestion> = mutableListOf()

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