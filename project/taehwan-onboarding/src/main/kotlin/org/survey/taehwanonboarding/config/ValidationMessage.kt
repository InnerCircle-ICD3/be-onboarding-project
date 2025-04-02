package org.survey.taehwanonboarding.config

enum class ValidationMessage(
    s: String,
) {
    IS_TITLE_REQUIRED("항목 제목은 필수입니다."),
    IS_REQUIRED_FIELD("필수 항목입니다."),
    IS_MAX_LENGTH_THOUSAND("최대 1000자까지 입력할 수 있습니다."),
    IS_OPTION_REQUIRED("선택형 항목은 옵션은 필수입니다."),
    IS_MIN_OPTION_COUNT_ONE("최소 1개 이상의 옵션을 선택해야합니다."),
    IS_REQUIRED_MIN_LENGTH_TEN("최소 10자 이상 입력해야합니다."),
}