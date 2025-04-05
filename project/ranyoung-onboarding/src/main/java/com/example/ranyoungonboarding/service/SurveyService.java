package com.example.ranyoungonboarding.service;

import com.example.ranyoungonboarding.api.CreateSurveyRequest;
import com.example.ranyoungonboarding.api.SurveyItemRequest;
import com.example.ranyoungonboarding.domain.Question;
import com.example.ranyoungonboarding.domain.QuestionType;
import com.example.ranyoungonboarding.domain.Survey;
import com.example.ranyoungonboarding.exception.ResourceNotFoundException;
import com.example.ranyoungonboarding.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    @Transactional
    public Survey createSurvey(CreateSurveyRequest request) {
        Survey survey = new Survey();
        survey.setName(request.getName());
        survey.setDescription(request.getDescription());

        // 질문 생성 및 추가
        int position = 0;
        for (SurveyItemRequest itemRequest : request.getQuestions()) {
            Question question = new Question();
            question.setName(itemRequest.getName());
            question.setDescription(itemRequest.getDescription());
            question.setType(itemRequest.getType());
            question.setRequired(itemRequest.getRequired());
            question.setPosition(position++);

            // 선택 옵션이 필요한 질문 타입에 대해서만 옵션 추가
            if (itemRequest.getType() == QuestionType.SINGLE_CHOICE ||
                    itemRequest.getType() == QuestionType.MULTIPLE_CHOICE) {
                question.setOptions(new ArrayList<>(itemRequest.getOptions()));
            }

            survey.addQuestion(question);
        }

        return surveyRepository.save(survey);
    }

    @Transactional
    public Survey updateSurvey(Long surveyId, CreateSurveyRequest request) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + surveyId));

        survey.setName(request.getName());
        survey.setDescription(request.getDescription());

        // 기존 질문을 모두 제거하고 새로운 질문으로 교체
        // (기존 응답은 연관관계 유지)
        survey.getQuestions().clear();

        // 새 질문 추가
        int position = 0;
        for (SurveyItemRequest itemRequest : request.getQuestions()) {
            Question question = new Question();
            question.setName(itemRequest.getName());
            question.setDescription(itemRequest.getDescription());
            question.setType(itemRequest.getType());
            question.setRequired(itemRequest.getRequired());
            question.setPosition(position++);

            if (itemRequest.getType() == QuestionType.SINGLE_CHOICE ||
                    itemRequest.getType() == QuestionType.MULTIPLE_CHOICE) {
                question.setOptions(new ArrayList<>(itemRequest.getOptions()));
            }

            survey.addQuestion(question);
        }

        return surveyRepository.save(survey);
    }

    @Transactional(readOnly = true)
    public Survey getSurvey(Long surveyId) {
        return surveyRepository.findById(surveyId)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + surveyId));
    }
}