package me.dhlee.donghyeononboarding.dto.request;

import java.util.List;

import org.springframework.util.ObjectUtils;

import me.dhlee.donghyeononboarding.domain.Survey;
import me.dhlee.donghyeononboarding.exception.AppException;
import me.dhlee.donghyeononboarding.exception.ErrorCode;

public record SurveyCreateRequest(
    String title,
    String description,
    List<SurveyItemCreateRequest> items
) {
    public SurveyCreateRequest {
        if (ObjectUtils.isEmpty(title)) {
            throw new AppException(ErrorCode.SURVEY_TITLE_IS_EMPTY);
        }
        if (ObjectUtils.isEmpty(description)) {
            throw new AppException(ErrorCode.SURVEY_DESCRIPTION_IS_EMPTY);
        }
        if (ObjectUtils.isEmpty(items)) {
            throw new AppException(ErrorCode.SURVEY_ITEM_IS_EMPTY);
        }
        if (items.size() >= 10) {
            throw new AppException(ErrorCode.SURVEY_ITEM_SIZE_OVERFLOW);
        }
    }
    public Survey toEntity() {
        return Survey.builder()
            .title(title)
            .description(description)
            .build();
    }
}
