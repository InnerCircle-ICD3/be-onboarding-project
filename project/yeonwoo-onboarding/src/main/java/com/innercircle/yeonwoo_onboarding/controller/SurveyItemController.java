package com.innercircle.yeonwoo_onboarding.controller;

import com.innercircle.yeonwoo_onboarding.domain.SurveyItem;
import com.innercircle.yeonwoo_onboarding.service.SurveyItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/survey-items")
@RequiredArgsConstructor
public class SurveyItemController {
    private final SurveyItemService surveyItemService;

    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<List<SurveyItem>> getBySurveyId(@PathVariable String surveyId) {
        return ResponseEntity.ok(surveyItemService.findBySurveyId(surveyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyItem> getById(@PathVariable String id) {
        return ResponseEntity.ok(surveyItemService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SurveyItem> create(@RequestBody SurveyItem surveyItem) {
        return ResponseEntity.ok(surveyItemService.createSurveyItem(surveyItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        surveyItemService.deleteSurveyItem(id);
        return ResponseEntity.noContent().build();
    }
}