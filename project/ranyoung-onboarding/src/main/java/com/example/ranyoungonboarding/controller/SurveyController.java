package com.example.ranyoungonboarding.controller;

import com.example.ranyoungonboarding.api.CreateSurveyRequest;
import com.example.ranyoungonboarding.api.SubmitResponseRequest;
import com.example.ranyoungonboarding.api.SurveyResponse;
import com.example.ranyoungonboarding.api.ResponseDto;
import com.example.ranyoungonboarding.domain.Response;
import com.example.ranyoungonboarding.domain.Survey;
import com.example.ranyoungonboarding.service.ResponseService;
import com.example.ranyoungonboarding.service.SurveyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;
    private final ResponseService responseService;

    // 1. 설문조사 생성 API
    @PostMapping
    public ResponseEntity<SurveyResponse> createSurvey(@Valid @RequestBody CreateSurveyRequest request) {
        Survey survey = surveyService.createSurvey(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SurveyResponse.fromEntity(survey));
    }

    // 2. 설문조사 수정 API
    @PutMapping("/{surveyId}")
    public ResponseEntity<SurveyResponse> updateSurvey(
            @PathVariable Long surveyId,
            @Valid @RequestBody CreateSurveyRequest request) {
        Survey survey = surveyService.updateSurvey(surveyId, request);
        return ResponseEntity.ok(SurveyResponse.fromEntity(survey));
    }

    // 3. 설문조사 응답 제출 API
    @PostMapping("/{surveyId}/responses")
    public ResponseEntity<ResponseDto> submitResponse(
            @PathVariable Long surveyId,
            @Valid @RequestBody SubmitResponseRequest request) {
        Response response = responseService.submitResponse(surveyId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.fromEntity(response));
    }

    // 4. 설문조사 응답 조회 API
    @GetMapping("/{surveyId}/responses")
    public ResponseEntity<Page<ResponseDto>> getSurveyResponses(
            @PathVariable Long surveyId,
            @RequestParam(required = false) String questionName,
            @RequestParam(required = false) String answerValue,
            Pageable pageable) {

        Page<Response> responses;
        if (questionName != null && answerValue != null) {
            // Advanced: 검색 기능 사용
            responses = responseService.searchSurveyResponses(surveyId, questionName, answerValue, pageable);
        } else {
            // 기본: 전체 응답 조회
            responses = responseService.getSurveyResponses(surveyId, pageable);
        }

        return ResponseEntity.ok(responses.map(ResponseDto::fromEntity));
    }

    // 추가: 설문조사 조회 API
    @GetMapping("/{surveyId}")
    public ResponseEntity<SurveyResponse> getSurvey(@PathVariable Long surveyId) {
        Survey survey = surveyService.getSurvey(surveyId);
        return ResponseEntity.ok(SurveyResponse.fromEntity(survey));
    }
}