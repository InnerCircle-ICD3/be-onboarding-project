package com.innercircle.yeonwoo_onboarding.controller;

import com.innercircle.yeonwoo_onboarding.domain.SurveyResponse;
import com.innercircle.yeonwoo_onboarding.service.SurveyResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/survey-responses")
@RequiredArgsConstructor
public class SurveyResponseController {
    private final SurveyResponseService surveyResponseService;

    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<List<SurveyResponse>> getBySurveyId(@PathVariable String surveyId) {
        return ResponseEntity.ok(surveyResponseService.findBySurveyId(surveyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(surveyResponseService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SurveyResponse> create(@RequestBody SurveyResponse response) {
        return ResponseEntity.ok(surveyResponseService.createResponse(response));
    }
}