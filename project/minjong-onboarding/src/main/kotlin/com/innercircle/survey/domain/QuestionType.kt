// src/main/kotlin/com/innercircle/survey/domain/QuestionType.kt
package com.innercircle.survey.domain

enum class QuestionType {
    SHORT_ANSWER,    // 단답형
    LONG_ANSWER,     // 장문형
    SINGLE_SELECT,   // 단일 선택 리스트
    MULTI_SELECT     // 다중 선택 리스트
}