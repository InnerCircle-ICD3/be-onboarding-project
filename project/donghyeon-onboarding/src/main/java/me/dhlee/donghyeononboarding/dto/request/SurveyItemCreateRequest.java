package me.dhlee.donghyeononboarding.dto.request;

import java.util.List;

import org.springframework.util.ObjectUtils;

import me.dhlee.donghyeononboarding.domain.ItemType;
import me.dhlee.donghyeononboarding.domain.Survey;
import me.dhlee.donghyeononboarding.domain.SurveyItem;
import me.dhlee.donghyeononboarding.exception.AppException;
import me.dhlee.donghyeononboarding.exception.ErrorCode;

public record SurveyItemCreateRequest(
    String title,
    String description,
    ItemType itemType,
    boolean isRequired,
    int displayOrder,
    List<SurveyItemOptionCreateRequest> options
) {
    public SurveyItemCreateRequest {
        if (ObjectUtils.isEmpty(title)) {
            throw new AppException(ErrorCode.SURVEY_ITEM_TITLE_IS_EMPTY);
        }
        if (ObjectUtils.isEmpty(description)) {
            throw new AppException(ErrorCode.SURVEY_ITEM_DESCRIPTION_IS_EMPTY);
        }
        if (itemType == null) {
            throw new AppException(ErrorCode.SURVEY_ITEM_TYPE_IS_EMPTY);
        }
        if (displayOrder < 0) {
            throw new AppException(ErrorCode.DISPLAY_ORDER_IS_NEGATIVE);
        }
        if (itemType.isSelectable()) {
            if (ObjectUtils.isEmpty(options)) {
                throw new AppException(ErrorCode.SURVEY_ITEM_OPTION_IS_EMPTY);
            }
            if (options.size() >= 10) {
                throw new AppException(ErrorCode.SURVEY_ITEM_OPTION_SIZE_OVERFLOW);
            }
        } else {
            if (!ObjectUtils.isEmpty(options)) {
                throw new AppException(ErrorCode.NOT_SELECTABLE_ITEM_TYPE_SHOULD_HAVE_NOT_OPTIONS);
            }
        }
    }
    public SurveyItem toEntity(Survey survey) {
        return SurveyItem.builder()
            .title(title)
            .description(description)
            .itemType(itemType)
            .isRequired(isRequired)
            .displayOrder(displayOrder)
            .survey(survey)
            .build();
    }
}
