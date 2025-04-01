package com.onboarding.form.domain

enum class QuestionType {
    SHORT, // 단답형
    LONG, // 장문형
    SINGLE_SELECT,  // 단일 선택 리스트
    MULTI_SELECT; // 다중 선택 리스트

    companion object {
        const val SHORT_VALUE = "SHORT"
        const val LONG_VALUE = "LONG"
        const val SINGLE_SELECT_VALUE = "SINGLE_SELECT"
        const val MULTI_SELECT_VALUE = "MULTI_SELECT"
    }
}