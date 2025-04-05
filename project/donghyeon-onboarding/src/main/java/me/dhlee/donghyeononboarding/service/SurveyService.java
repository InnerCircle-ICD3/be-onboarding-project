package me.dhlee.donghyeononboarding.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import me.dhlee.donghyeononboarding.domain.Survey;
import me.dhlee.donghyeononboarding.domain.SurveyItem;
import me.dhlee.donghyeononboarding.domain.SurveyItemOption;
import me.dhlee.donghyeononboarding.dto.request.SurveyCreateRequest;
import me.dhlee.donghyeononboarding.dto.request.SurveyItemCreateRequest;
import me.dhlee.donghyeononboarding.dto.request.SurveyItemOptionCreateRequest;
import me.dhlee.donghyeononboarding.dto.response.SurveyCreateResponse;
import me.dhlee.donghyeononboarding.exception.AppException;
import me.dhlee.donghyeononboarding.exception.ErrorCode;
import me.dhlee.donghyeononboarding.mapper.SurveyMapper;
import me.dhlee.donghyeononboarding.repository.SurveyRepository;

@RequiredArgsConstructor
@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyMapper surveyMapper;

    @Transactional
    public SurveyCreateResponse createSurvey(SurveyCreateRequest request) {
        Survey survey = surveyMapper.toEntity(request);
        return SurveyCreateResponse.from(surveyRepository.save(survey));
    }
}
