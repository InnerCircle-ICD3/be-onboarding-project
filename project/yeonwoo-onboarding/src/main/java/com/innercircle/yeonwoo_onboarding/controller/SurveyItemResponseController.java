package com.innercircle.yeonwoo_onboarding.controller;

import com.innercircle.yeonwoo_onboarding.domain.SurveyItemResponse;
import com.innercircle.yeonwoo_onboarding.service.SurveyItemResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/survey-item-responses")
@RequiredArgsConstructor
public class SurveyItemResponseController {
    private final SurveyItemResponseService surveyItemResponseService;

    @GetMapping("/survey-response/{surveyResponseId}")
    public ResponseEntity<List<SurveyItemResponse>> getBySurveyResponseId(@PathVariable String surveyResponseId) {
        return ResponseEntity.ok(surveyItemResponseService.findBySurveyResponseId(surveyResponseId));
    }

    @PostMapping
    public ResponseEntity<SurveyItemResponse> create(@RequestBody SurveyItemResponse response) {
        return ResponseEntity.ok(surveyItemResponseService.createResponse(response));
    }
}