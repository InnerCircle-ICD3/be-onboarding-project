package com.chosseang.seonghunonboarding.dto

import com.chosseang.seonghunonboarding.entity.Answer
import com.chosseang.seonghunonboarding.enum.ItemType

data class AnswerSubmitRequest(
    val name: String,
    val items: Map<String, QuestionItem>,  // 질문 ID를 키로, QuestionItem을 값으로 하는 맵
    val responses: Map<String, List<String>>,  // 질문 ID를 키로, 응답을 값으로 하는 맵
    val surveyId: Long
)

data class AnswerResponse(
    val id: Long?,
    val name: String,
    val items: Map<String, QuestionItem>,
    val responses: Map<String, List<String>>
)

data class QuestionItem(
    val text: String,                  // 질문 텍스트
    val type: ItemType,                // 질문 유형 (ShortAnswer, LongAnswer, SingleSelect, MultiSelect)
    val options: List<String>,         // 선택 옵션 목록
    val required: Boolean = false      // 필수 응답 여부
)
