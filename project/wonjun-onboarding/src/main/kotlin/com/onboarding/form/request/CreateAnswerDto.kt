import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.onboarding.form.domain.QuestionType


data class CreateAnswerRequestDto(
    val version: Int,
    val answers: List<CreateAnswerDto>
)

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "questionType",
    visible = true
)
@JsonSubTypes(
    JsonSubTypes.Type(name = QuestionType.SHORT_VALUE, value = CreateInsertAnswerDto::class),
    JsonSubTypes.Type(name = QuestionType.LONG_VALUE, value = CreateInsertAnswerDto::class),
    JsonSubTypes.Type(name = QuestionType.SINGLE_SELECT_VALUE, value = CreateSelectAnswerDto::class),
    JsonSubTypes.Type(name = QuestionType.MULTI_SELECT_VALUE, value = CreateSelectAnswerDto::class),
)
sealed class CreateAnswerDto(
    open val questionId: Long,
    open val questionType: QuestionType,
)

data class CreateInsertAnswerDto(
    override val questionId: Long,
    override val questionType: QuestionType,
    val content: String
) : CreateAnswerDto(questionId, questionType)

data class CreateSelectAnswerDto(
    override val questionId: Long,
    override val questionType: QuestionType,
    val selected: List<String>
) : CreateAnswerDto(questionId, questionType)