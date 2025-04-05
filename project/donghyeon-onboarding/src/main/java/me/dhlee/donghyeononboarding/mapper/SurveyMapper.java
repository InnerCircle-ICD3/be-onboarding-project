package me.dhlee.donghyeononboarding.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import me.dhlee.donghyeononboarding.domain.Survey;
import me.dhlee.donghyeononboarding.domain.SurveyItem;
import me.dhlee.donghyeononboarding.dto.request.SurveyCreateRequest;
import me.dhlee.donghyeononboarding.dto.request.SurveyItemCreateRequest;
import me.dhlee.donghyeononboarding.dto.request.SurveyItemOptionCreateRequest;

@Component
public class SurveyMapper {

    public Survey toEntity(SurveyCreateRequest request) {
        Survey survey = request.toEntity();
        for (SurveyItemCreateRequest item : request.items()) {
            SurveyItem surveyItem = item.toEntity(survey);
            List<SurveyItemOptionCreateRequest> options = item.options();
            for (SurveyItemOptionCreateRequest option : options) {
                surveyItem.addOption(option.toEntity(surveyItem));
            }
            survey.addItem(surveyItem);
        }
        return survey;
    }
}
