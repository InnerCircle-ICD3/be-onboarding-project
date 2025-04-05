package com.example.doohyeononboarding.api.service.request

import com.example.doohyeononboarding.domain.question.QuestionType

class QuestionRequest(
    val questionId: Long? = null,
    val title: String,
    val description: String? = "",
    val type: QuestionType,
    val isRequired: Boolean,
    val options: MutableList<String>? = null // 선택형일 때만 필요
) {

    init {
        require(title.isNotBlank()) { "질문 제목은 필수입니다." }
    }

}

