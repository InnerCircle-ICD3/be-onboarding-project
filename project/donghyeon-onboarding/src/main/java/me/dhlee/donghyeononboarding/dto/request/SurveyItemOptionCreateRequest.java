package me.dhlee.donghyeononboarding.dto.request;

import org.springframework.util.ObjectUtils;

import me.dhlee.donghyeononboarding.domain.SurveyItem;
import me.dhlee.donghyeononboarding.domain.SurveyItemOption;
import me.dhlee.donghyeononboarding.exception.AppException;
import me.dhlee.donghyeononboarding.exception.ErrorCode;

public record SurveyItemOptionCreateRequest(
    String title,
    int displayOrder
) {
    public SurveyItemOptionCreateRequest {
        if (ObjectUtils.isEmpty(title)) {
            throw new AppException(ErrorCode.SURVEY_ITEM_OPTION_TITLE_IS_EMPTY);
        }
        if (displayOrder < 0) {
            throw new AppException(ErrorCode.DISPLAY_ORDER_IS_NEGATIVE);
        }
    }
    public SurveyItemOption toEntity(SurveyItem surveyItem) {
        return SurveyItemOption.builder()
            .title(title)
            .displayOrder(displayOrder)
            .surveyItem(surveyItem)
            .build();
    }
}
