package com.innercircle.yeonwoo_onboarding.controller;

import com.innercircle.yeonwoo_onboarding.domain.Survey;
import com.innercircle.yeonwoo_onboarding.dto.SurveyCreateDto;
import com.innercircle.yeonwoo_onboarding.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @GetMapping
    public ResponseEntity<List<Survey>> getAllSurveys() {
        return ResponseEntity.ok(surveyService.findAllSurveys());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Survey> getSurveyById(@PathVariable String id) {
        return ResponseEntity.ok(surveyService.findSurveyById(id));
    }

    @PostMapping
    public ResponseEntity<Survey> createSurvey(@RequestBody SurveyCreateDto surveyDto) {
        try {
            Survey survey = surveyService.createSurvey(surveyDto);
            return ResponseEntity.ok(survey);
        } catch (Exception e) {
            e.printStackTrace(); // 로그 추적
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Survey> updateSurvey(@PathVariable String id, @RequestBody Survey survey) {
        return ResponseEntity.ok(surveyService.updateSurvey(id, survey));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable String id) {
        surveyService.deleteSurvey(id);
        return ResponseEntity.noContent().build();
    }
}