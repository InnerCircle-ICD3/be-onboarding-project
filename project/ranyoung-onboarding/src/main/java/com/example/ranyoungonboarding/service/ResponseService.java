package com.example.ranyoungonboarding.service;

import com.example.ranyoungonboarding.api.SubmitResponseRequest;
import com.example.ranyoungonboarding.api.AnswerRequest;
import com.example.ranyoungonboarding.domain.*;
import com.example.ranyoungonboarding.exception.BadRequestException;
import com.example.ranyoungonboarding.exception.ResourceNotFoundException;
import com.example.ranyoungonboarding.repository.QuestionRepository;
import com.example.ranyoungonboarding.repository.ResponseRepository;
import com.example.ranyoungonboarding.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResponseService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ResponseRepository responseRepository;

    @Transactional
    public Response submitResponse(Long surveyId, SubmitResponseRequest request) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + surveyId));

        // 설문조사의 모든 질문 조회 및 매핑
        List<Question> questions = questionRepository.findBySurveyId(surveyId);
        Map<Long, Question> questionMap = new HashMap<>();
        for (Question question : questions) {
            questionMap.put(question.getId(), question);
        }

        // 필수 질문 응답 확인
        for (Question question : questions) {
            if (question.getRequired() &&
                    request.getAnswers().stream()
                            .noneMatch(a -> a.getQuestionId().equals(question.getId()))) {
                throw new BadRequestException("Required question not answered: " + question.getName());
            }
        }

        // 응답 객체 생성
        Response response = new Response();
        response.setSurvey(survey);

        // 각 답변 추가
        for (AnswerRequest answerRequest : request.getAnswers()) {
            Question question = questionMap.get(answerRequest.getQuestionId());
            if (question == null) {
                throw new BadRequestException("Question not found with id: " + answerRequest.getQuestionId());
            }

            // 질문 유형에 따른 유효성 검증
            validateAnswerByQuestionType(question, answerRequest);

            // 답변 생성
            Answer answer = new Answer();
            answer.setQuestion(question);

            // 질문 유형에 따라 답변 값 설정
            if (question.getType() == QuestionType.MULTIPLE_CHOICE) {
                answer.setMultipleValues(answerRequest.getValues());
            } else {
                answer.setTextValue(answerRequest.getValue());
            }

            response.addAnswer(answer);
        }

        return responseRepository.save(response);
    }

    private void validateAnswerByQuestionType(Question question, AnswerRequest answerRequest) {
        switch (question.getType()) {
            case SHORT_ANSWER:
            case LONG_ANSWER:
                if (!StringUtils.hasText(answerRequest.getValue())) {
                    throw new BadRequestException("Text answer is required for question: " + question.getName());
                }
                break;

            case SINGLE_CHOICE:
                if (!StringUtils.hasText(answerRequest.getValue()) ||
                        !question.getOptions().contains(answerRequest.getValue())) {
                    throw new BadRequestException("Invalid choice for question: " + question.getName());
                }
                break;

            case MULTIPLE_CHOICE:
                if (answerRequest.getValues() == null || answerRequest.getValues().isEmpty()) {
                    throw new BadRequestException("At least one choice is required for question: " + question.getName());
                }
                for (String value : answerRequest.getValues()) {
                    if (!question.getOptions().contains(value)) {
                        throw new BadRequestException("Invalid choice: " + value + " for question: " + question.getName());
                    }
                }
                break;
        }
    }

    @Transactional(readOnly = true)
    public Page<Response> getSurveyResponses(Long surveyId, Pageable pageable) {
        // 설문조사 존재 여부 확인
        if (!surveyRepository.existsById(surveyId)) {
            throw new ResourceNotFoundException("Survey not found with id: " + surveyId);
        }

        return responseRepository.findBySurveyId(surveyId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Response> searchSurveyResponses(Long surveyId, String questionName,
                                                String answerValue, Pageable pageable) {
        // 설문조사 존재 여부 확인
        if (!surveyRepository.existsById(surveyId)) {
            throw new ResourceNotFoundException("Survey not found with id: " + surveyId);
        }

        return responseRepository.searchByQuestionAndAnswer(surveyId, questionName, answerValue, pageable);
    }
}