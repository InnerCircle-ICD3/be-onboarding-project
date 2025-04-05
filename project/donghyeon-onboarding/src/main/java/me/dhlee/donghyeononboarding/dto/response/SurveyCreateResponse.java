package me.dhlee.donghyeononboarding.dto.response;

import me.dhlee.donghyeononboarding.domain.Survey;

public record SurveyCreateResponse(Long surveyId) {
    public static SurveyCreateResponse from(Survey save) {
        return new SurveyCreateResponse(save.getId());
    }
}
