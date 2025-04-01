package com.example

import com.example.dto.*
import com.example.entity.*
import com.example.exception.*
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import com.example.service.SurveyAnswerService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.util.Optional

class SurveyAnswerServiceTest {

    private val surveyRepository = mock<SurveyRepository>()
    private val surveyAnswerRepository = mock<SurveyAnswerRepository>()
    private val surveyAnswerService = SurveyAnswerService(surveyRepository, surveyAnswerRepository)

    @Test
    @DisplayName("Should save successfully when submitting valid answers")
    fun shouldSaveSuccessfullyWhenSubmittingValidAnswers() {
        val survey = Survey(id = 1L, title = "Answer Submission Test Survey", description = "Test for submitting and retrieving responses")

        val singleChoiceItem = ChoiceItem(
            name = "Language Choice",
            description = "Choose a language",
            isRequired = true,
            isMultiple = false,
            survey = survey
        ).apply { id = 1L }

        val shortTextItem = TextItem(
            name = "Experience",
            description = "Years of experience",
            isRequired = false,
            isLong = false,
            survey = survey
        ).apply { id = 2L }

        val kotlinOption = SelectionOption(id = 10L, value = "Kotlin", item = singleChoiceItem)
        val javaOption = SelectionOption(value = "Java", item = singleChoiceItem)
        singleChoiceItem.options.addAll(listOf(kotlinOption, javaOption))

        survey.items.addAll(listOf(singleChoiceItem, shortTextItem))

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                ChoiceAnswerDto(itemId = singleChoiceItem.id, selectedOptionIds = listOf(kotlinOption.id)),
                TextAnswerDto(itemId = shortTextItem.id, value = "3 years")
            )
        )

        assertDoesNotThrow {
            surveyAnswerService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).saveAll(any<List<SurveyAnswerBase>>())
    }

    @Test
    @DisplayName("Should throw exception when answer value does not match survey item options")
    fun shouldThrowExceptionWhenAnswerValueDoesNotMatchSurveyItemOptions() {
        val survey = Survey(id = 1L, title = "Answer Value Match Test", description = "Test if the answer matches the options")

        val item = ChoiceItem(
            name = "Language Choice",
            description = "Choose a language",
            isRequired = true,
            isMultiple = false,
            survey = survey
        ).apply { id = 1L }

        val kotlin = SelectionOption(id = 100L, value = "Kotlin", item = item)
        val java = SelectionOption(id = 101L, value = "Java", item = item)
        item.options.addAll(listOf(kotlin, java))
        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                ChoiceAnswerDto(itemId = item.id, selectedOptionIds = listOf(999L))
            )
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            surveyAnswerService.submitAnswer(1L, request)
        }

        assertEquals("One or more selected option IDs are invalid.", exception.message)
    }

    @Test
    @DisplayName("Should throw exception when survey does not exist")
    fun shouldThrowExceptionWhenSurveyDoesNotExist() {
        whenever(surveyRepository.findById(999L)).thenReturn(Optional.empty())

        val request = AnswerSubmitDto(
            answers = listOf(TextAnswerDto(itemId = 1L, value = "Kotlin"))
        )

        val exception = assertThrows(SurveyNotFoundException::class.java) {
            surveyAnswerService.submitAnswer(999L, request)
        }

        assertEquals(ErrorCode.SURVEY_NOT_FOUND.message, exception.message)
    }

    @Test
    @DisplayName("Should save multiple choice answers correctly")
    fun shouldSaveMultipleChoiceAnswersCorrectly() {
        val survey = Survey(id = 1L, title = "Programming Language Survey", description = "Select all the languages you use")

        val item = ChoiceItem(
            name = "Language Choice",
            description = "Select all the languages you use",
            isRequired = true,
            isMultiple = true,
            survey = survey
        ).apply { id = 1L }

        val kotlin = SelectionOption(id = 100L, value = "Kotlin", item = item)
        val java = SelectionOption(id = 101L, value = "Java", item = item)
        item.options.addAll(listOf(kotlin, java))
        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                ChoiceAnswerDto(itemId = item.id, selectedOptionIds = listOf(kotlin.id, java.id))
            )
        )

        assertDoesNotThrow {
            surveyAnswerService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).saveAll(any<List<SurveyAnswerBase>>())
    }

    @Test
    @DisplayName("Should throw exception when one or more choices are not valid options")
    fun shouldThrowExceptionWhenChoiceIsNotValid() {
        val survey = Survey(id = 1L, title = "Language Selection Survey", description = "Multiple choice test")

        val item = ChoiceItem(
            name = "Language",
            description = "Languages used",
            isRequired = true,
            isMultiple = true,
            survey = survey
        ).apply { id = 1L }

        val kotlin = SelectionOption(id = 200L, value = "Kotlin", item = item)
        val java = SelectionOption(id = 201L, value = "Java", item = item)
        val python = SelectionOption(id = 202L, value = "Python", item = item)
        item.options.addAll(listOf(kotlin, java, python))
        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                ChoiceAnswerDto(itemId = item.id, selectedOptionIds = listOf(kotlin.id, 999L))
            )
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            surveyAnswerService.submitAnswer(1L, request)
        }

        assertEquals("One or more selected option IDs are invalid.", exception.message)
    }

    @Test
    @DisplayName("Should throw exception when required question is missing in answers")
    fun shouldThrowExceptionWhenRequiredQuestionIsMissing() {
        val survey = Survey(id = 1L, title = "Required Field Survey", description = "Test for required field")

        val requiredTextItem = TextItem(
            name = "Your Name",
            description = "Please enter your name",
            isRequired = true,
            isLong = false,
            survey = survey
        ).apply { id = 1L }

        val optionalItem = TextItem(
            name = "Your Hobby",
            description = "Optional hobby",
            isRequired = false,
            isLong = false,
            survey = survey
        ).apply { id = 2L }

        survey.items.addAll(listOf(requiredTextItem, optionalItem))

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                TextAnswerDto(itemId = optionalItem.id, value = "Coding")
            )
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            surveyAnswerService.submitAnswer(1L, request)
        }

        assertEquals("Required questions must be answered.", exception.message)
    }

    @Test
    @DisplayName("Should throw exception when short text exceeds 255 characters")
    fun shouldThrowExceptionWhenShortTextExceedsLimit() {
        val survey = Survey(id = 1L, title = "Short Text Limit Test", description = "Check short text length")

        val item = TextItem(
            name = "Introduce Yourself",
            description = "Short intro",
            isRequired = true,
            isLong = false,
            survey = survey
        ).apply { id = 1L }

        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val longText = "a".repeat(256)

        val request = AnswerSubmitDto(
            answers = listOf(TextAnswerDto(itemId = item.id, value = longText))
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            surveyAnswerService.submitAnswer(1L, request)
        }

        assertEquals("SHORT_TEXT answers must be within 255 characters.", exception.message)
    }

    @Test
    @DisplayName("Should throw exception when answer type mismatches item type")
    fun shouldThrowExceptionWhenAnswerTypeMismatch() {
        val survey = Survey(id = 1L, title = "Type Mismatch Test", description = "Invalid DTO for item")

        val item = ChoiceItem(
            name = "Language",
            description = "Select your language",
            isRequired = true,
            isMultiple = false,
            survey = survey
        ).apply { id = 1L }

        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(TextAnswerDto(itemId = item.id, value = "Kotlin"))  // 잘못된 타입!
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            surveyAnswerService.submitAnswer(1L, request)
        }

        assertEquals("Item is not of type text.", exception.message)
    }

    @Test
    @DisplayName("Should save valid single selection for multiple choice item")
    fun shouldAllowSingleAnswerForMultipleChoice() {
        val survey = Survey(id = 1L, title = "Multi to Single", description = "One answer to multi item")

        val item = ChoiceItem(
            name = "Tech Stack",
            description = "Select used stacks",
            isRequired = true,
            isMultiple = true,
            survey = survey
        ).apply { id = 1L }

        val spring = SelectionOption(id = 1L, value = "Spring", item = item)
        val flask = SelectionOption(id = 2L, value = "Flask", item = item)
        item.options.addAll(listOf(spring, flask))
        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(ChoiceAnswerDto(itemId = item.id, selectedOptionIds = listOf(1L)))
        )

        assertDoesNotThrow {
            surveyAnswerService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).saveAll(any<List<SurveyAnswerBase>>())
    }

    @Test
    @DisplayName("Should allow long text for long text item")
    fun shouldAllowLongTextForLongTextItem() {
        val survey = Survey(id = 1L, title = "Long Text Test", description = "Allow long answers")

        val item = TextItem(
            name = "Essay",
            description = "Write an essay",
            isRequired = true,
            isLong = true,
            survey = survey
        ).apply { id = 1L }

        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val longText = "a".repeat(1000)

        val request = AnswerSubmitDto(
            answers = listOf(TextAnswerDto(itemId = item.id, value = longText))
        )

        assertDoesNotThrow {
            surveyAnswerService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).saveAll(any<List<SurveyAnswerBase>>())
    }

    @Test
    @DisplayName("Should throw exception when required text answer is empty")
    fun shouldThrowExceptionWhenRequiredAnswerIsEmpty() {
        val survey = Survey(id = 1L, title = "Empty Answer Test", description = "Required field with empty value")

        val item = TextItem(
            name = "Name",
            description = "Enter your name",
            isRequired = true,
            isLong = false,
            survey = survey
        ).apply { id = 1L }

        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(TextAnswerDto(itemId = item.id, value = "   "))
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            surveyAnswerService.submitAnswer(1L, request)
        }

        assertEquals("Required questions must be answered.", exception.message)
    }

    @Test
    @DisplayName("Should handle duplicate option IDs gracefully")
    fun shouldHandleDuplicateOptionIds() {
        val survey = Survey(id = 1L, title = "Duplicate Option Test", description = "Same option selected multiple times")

        val item = ChoiceItem(
            name = "Language",
            description = "Select language(s)",
            isRequired = true,
            isMultiple = true,
            survey = survey
        ).apply { id = 1L }

        val kotlin = SelectionOption(id = 100L, value = "Kotlin", item = item)
        item.options.add(kotlin)
        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(ChoiceAnswerDto(itemId = item.id, selectedOptionIds = listOf(100L, 100L)))
        )

        // 중복 허용 여부는 설계에 따라 다르지만, 현재 로직상 중복 선택도 유효한 옵션으로 판단됨
        assertDoesNotThrow {
            surveyAnswerService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).saveAll(any<List<SurveyAnswerBase>>())
    }

    @Test
    @DisplayName("Should save snapshot values correctly in answers")
    fun shouldSaveSnapshotValuesCorrectlyInAnswers() {
        val survey = Survey(id = 1L, title = "Snapshot Test", description = "Testing snapshot fields")
    
        val choiceItem = ChoiceItem(
            name = "Favorite Language",
            description = "Pick one",
            isRequired = true,
            isMultiple = false,
            survey = survey
        ).apply { id = 1L }
    
        val option = SelectionOption(id = 10L, value = "Kotlin", item = choiceItem)
        choiceItem.options.add(option)
    
        val textItem = TextItem(
            name = "Comment",
            description = "Additional thoughts",
            isRequired = false,
            isLong = false,
            survey = survey
        ).apply { id = 2L }
    
        survey.items.addAll(listOf(choiceItem, textItem))
    
        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))
    
        val request = AnswerSubmitDto(
            answers = listOf(
                ChoiceAnswerDto(itemId = choiceItem.id, selectedOptionIds = listOf(option.id)),
                TextAnswerDto(itemId = textItem.id, value = "Just love Kotlin!")
            )
        )
    
        val captor = argumentCaptor<List<SurveyAnswerBase>>()
    
        surveyAnswerService.submitAnswer(1L, request)
    
        verify(surveyAnswerRepository).saveAll(captor.capture())
        val savedAnswers = captor.firstValue
    
        // 검증: 스냅샷 필드가 제대로 들어갔는지 확인
        val choiceAnswer = savedAnswers.find { it.questionType == "CHOICE" }
        val textAnswer = savedAnswers.find { it.questionType == "TEXT" }
    
        assertEquals("Favorite Language", choiceAnswer?.questionName)
        assertEquals("CHOICE", choiceAnswer?.questionType)
    
        assertEquals("Comment", textAnswer?.questionName)
        assertEquals("TEXT", textAnswer?.questionType)
    }    
}