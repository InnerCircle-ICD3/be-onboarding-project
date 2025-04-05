package com.innercircle.api.survey.controller

import com.innercircle.api.survey.controller.request.*
import com.innercircle.api.survey.controller.response.SurveyResponse
import com.innercircle.survey.entity.QuestionType
import java.time.LocalDateTime

fun 장문형_설문_생성_요청() = SurveyCreateRequest(
    name = "설문 이름",
    description = "설문 설명",
    startAt = LocalDateTime.now(),
    endAt = LocalDateTime.now().plusWeeks(1),
    participantCapacity = 10,
    questions = listOf(
        SurveyQuestionCreateRequest(
            name = "백엔드 개발자를 선택한 이유는?",
            questionType = QuestionType.LONG_ANSWER.name,
            description = "프론트엔드를 하지 않은 이유를 중점으로 논리적으로 서술하시오.",
            required = true
        )
    )
)

fun 단답형_설문_생성_요청() = SurveyCreateRequest(
    name = "설문 이름",
    description = "설문 설명",
    startAt = LocalDateTime.now(),
    endAt = LocalDateTime.now().plusWeeks(1),
    participantCapacity = 10,
    questions = listOf(
        SurveyQuestionCreateRequest(
            name = "첫사랑 이름은?",
            questionType = QuestionType.SHORT_ANSWER.name,
            description = "없으면 어머니 성함을 적으세요",
            required = true
        )
    )
)

fun 다중_선택형_설문_생성_요청() = SurveyCreateRequest(
    name = "설문 이름",
    description = "설문 설명",
    startAt = LocalDateTime.now(),
    endAt = LocalDateTime.now().plusWeeks(1),
    participantCapacity = 10,
    questions = listOf(
        SurveyQuestionCreateRequest(
            name = "다음중 월클 라인에서 나가야할 대상은?",
            questionType = QuestionType.MULTI_CHOICE.name,
            description = "월클 라인에서 나가야할 대상을 선택해주세요.",
            options = listOf(
                SurveyQuestionOptionCreateRequest(content = "BTS"),
                SurveyQuestionOptionCreateRequest(content = "봉준호"),
                SurveyQuestionOptionCreateRequest(content = "손흥민"),
                SurveyQuestionOptionCreateRequest(content = "제이팍"),
                SurveyQuestionOptionCreateRequest(content = "레츠고")
            ),
            required = true
        )
    )
)

fun 단일_선택형_설문_생성_요청() = SurveyCreateRequest(
    name = "설문 이름",
    description = "설문 설명",
    startAt = LocalDateTime.now(),
    endAt = LocalDateTime.now().plusWeeks(1),
    participantCapacity = 10,
    questions = listOf(
        SurveyQuestionCreateRequest(
            name = "다음중 월클 라인에서 나가야할 대상은?",
            questionType = QuestionType.SINGLE_CHOICE.name,
            description = "월클 라인에서 나가야할 대상을 선택해주세요.",
            options = listOf(
                SurveyQuestionOptionCreateRequest(content = "BTS"),
                SurveyQuestionOptionCreateRequest(content = "봉준호"),
                SurveyQuestionOptionCreateRequest(content = "손흥민"),
                SurveyQuestionOptionCreateRequest(content = "제이팍"),
                SurveyQuestionOptionCreateRequest(content = "레츠고")
            ),
            required = true
        )
    )
)

fun 단일_선택형_설문_수정_요청(surveyResponse: SurveyResponse) = SurveyUpdateRequest(
    name = "수정 된 설문 이름",
    description = "수정 된 설문 설명",
    startAt = LocalDateTime.now(),
    endAt = LocalDateTime.now().plusWeeks(1),
    participantCapacity = 10,
    questions = listOf(
        SurveyQuestionUpdateRequest(
            id = surveyResponse.questions?.get(0)?.id,
            name = "수정 된 다음중 월클 라인에서 나가야할 대상은?",
            questionType = QuestionType.SINGLE_CHOICE.name,
            description = "수정 된 월클 라인에서 나가야할 대상을 선택해주세요.",
            options = listOf(
                SurveyQuestionOptionUpdateRequest(
                    id = surveyResponse.questions?.getOrNull(0)?.options?.getOrNull(0)?.id,
                    content = "수정 된 BTS"
                ),
                SurveyQuestionOptionUpdateRequest(
                    id = surveyResponse.questions?.getOrNull(0)?.options?.getOrNull(1)?.id,
                    content = "수정 된 봉준호"
                ),
                SurveyQuestionOptionUpdateRequest(
                    id = surveyResponse.questions?.getOrNull(0)?.options?.getOrNull(2)?.id,
                    content = "수정 된 손흥민"
                ),
                SurveyQuestionOptionUpdateRequest(
                    id = surveyResponse.questions?.getOrNull(0)?.options?.getOrNull(3)?.id,
                    content = "수정 된 제이팍"
                ),
                SurveyQuestionOptionUpdateRequest(
                    id = surveyResponse.questions?.getOrNull(0)?.options?.getOrNull(4)?.id,
                    content = "수정 된 레츠고"
                )
            ),
            required = true
        )
    )
)


fun 장문형_설문_수정_요청(surveyResponse: SurveyResponse) = SurveyUpdateRequest(
    name = "수정 된 설문 이름",
    description = "수정 된 설문 설명",
    startAt = LocalDateTime.now(),
    endAt = LocalDateTime.now().plusWeeks(1),
    participantCapacity = 10,
    questions = listOf(
        SurveyQuestionUpdateRequest(
            id = surveyResponse.questions?.get(0)?.id,
            name = "수정 된 백엔드 개발자를 선택한 이유는?",
            questionType = QuestionType.LONG_ANSWER.name,
            description = "수정 된 프론트엔드를 하지 않은 이유를 중점으로 논리적으로 서술하시오.",
            required = true
        )
    )
)

fun 단답형_설문_수정_요청(surveyResponse: SurveyResponse) = SurveyUpdateRequest(
    name = "수정 된 설문 이름",
    description = "수정 된 설문 설명",
    startAt = LocalDateTime.now(),
    endAt = LocalDateTime.now().plusWeeks(1),
    participantCapacity = 10,
    questions = listOf(
        SurveyQuestionUpdateRequest(
            id = surveyResponse.questions?.get(0)?.id,
            name = "수정 된 첫사랑 이름은?",
            questionType = QuestionType.SHORT_ANSWER.name,
            description = "수정 된 없으면 어머니 성함을 적으세요",
            required = true
        )
    )
)

fun 다중_선택형_설문_수정_요청(surveyResponse: SurveyResponse) = SurveyUpdateRequest(
    name = "수정 된 설문 이름",
    description = "수정 된 설문 설명",
    startAt = LocalDateTime.now(),
    endAt = LocalDateTime.now().plusWeeks(1),
    participantCapacity = 10,
    questions = listOf(
        SurveyQuestionUpdateRequest(
            id = surveyResponse.questions?.get(0)?.id,
            name = "수정 된 다음중 월클 라인에서 나가야할 대상은?",
            questionType = QuestionType.MULTI_CHOICE.name,
            description = "수정 된 월클 라인에서 나가야할 대상을 선택해주세요.",
            options = listOf(
                SurveyQuestionOptionUpdateRequest(
                    id = surveyResponse.questions?.getOrNull(0)?.options?.getOrNull(0)?.id,
                    content = "수정 된 BTS"
                ),
                SurveyQuestionOptionUpdateRequest(
                    id = surveyResponse.questions?.getOrNull(0)?.options?.getOrNull(1)?.id,
                    content = "수정 된 봉준호"
                ),
                SurveyQuestionOptionUpdateRequest(
                    id = surveyResponse.questions?.getOrNull(0)?.options?.getOrNull(2)?.id,
                    content = "수정 된 손흥민"
                ),
                SurveyQuestionOptionUpdateRequest(
                    id = surveyResponse.questions?.getOrNull(0)?.options?.getOrNull(3)?.id,
                    content = "수정 된 제이팍"
                ),
                SurveyQuestionOptionUpdateRequest(
                    id = surveyResponse.questions?.getOrNull(0)?.options?.getOrNull(4)?.id,
                    content = "수정 된 레츠고"
                )
            ),
            required = true
        )
    )
)

fun 단일_선택형_설문_답변_요청(questionId: Long?, questionOptionId: Long?): SurveyAnswerCreateRequest =
    SurveyAnswerCreateRequest(
        surveyQuestionId = questionId,
        surveyName = "단일 선택형 설문",
        surveyDescription = "단일 선택형 설문 설명",
        surveyQuestionName = "단일 선택형 질문",
        surveyQuestionDescription = "단일 선택형 질문 설명",
        questionType = QuestionType.SINGLE_CHOICE.name,
        content = "단일 선택형 선택지 내용",
        options = listOf(
            SurveyAnswerOptionCreateRequest(
                optionId = questionOptionId,
                content = "단일 선택형 선택지 내용"
            )
        )
    )

fun 다중_선택형_설문_답변_요청(questionId: Long?, vararg questionOptionIds: Long?): SurveyAnswerCreateRequest =
    SurveyAnswerCreateRequest(
        surveyQuestionId = questionId,
        surveyName = "다중 선택형 설문",
        surveyDescription = "다중 선택형 설문 설명",
        surveyQuestionName = "다중 선택형 질문",
        surveyQuestionDescription = "다중 선택형 질문 설명",
        questionType = QuestionType.SINGLE_CHOICE.name,
        content = "다중 선택형 선택지 내용",
        options = questionOptionIds.map {
            SurveyAnswerOptionCreateRequest(
                optionId = it,
                content = "다중 선택형 선택지 내용"
            )
        }
    )

fun 단답형_설문_답변_요청(questionId: Long?): SurveyAnswerCreateRequest =
    SurveyAnswerCreateRequest(
        surveyQuestionId = questionId,
        surveyName = "단답형 설문",
        surveyDescription = "단답형 설문 설명",
        surveyQuestionName = "단답형 질문",
        surveyQuestionDescription = "단답형 질문 설명",
        questionType = QuestionType.SHORT_ANSWER.name,
        content = "단답형 설문 답변 내용",
    )

fun 장문형_설문_답변_요청(questionId: Long?): SurveyAnswerCreateRequest =
    SurveyAnswerCreateRequest(
        surveyQuestionId = questionId,
        surveyName = "장문형 설문",
        surveyDescription = "장문형 설문 설명",
        surveyQuestionName = "장문형 질문",
        surveyQuestionDescription = "장문형 질문 설명",
        questionType = QuestionType.LONG_ANSWER.name,
        content = "장문형 설문 답변 내용",
    )
