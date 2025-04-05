package onboarding.survey.data.survey.type

enum class SurveyQuestionStatus {
    ACTIVE, // 활성화
    INACTIVE, // 비활성화 -> 업데이트 되어 다른 버전이 활성화된 상태
    DELETED // 삭제됨
}