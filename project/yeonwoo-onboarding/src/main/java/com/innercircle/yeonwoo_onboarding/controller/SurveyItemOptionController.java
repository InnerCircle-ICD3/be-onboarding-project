package com.innercircle.yeonwoo_onboarding.controller;

import com.innercircle.yeonwoo_onboarding.domain.SurveyItemOption;
import com.innercircle.yeonwoo_onboarding.service.SurveyItemOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/survey-item-options")
@RequiredArgsConstructor
public class SurveyItemOptionController {
    private final SurveyItemOptionService surveyItemOptionService;

    @GetMapping("/survey-item/{surveyItemId}")
    public ResponseEntity<List<SurveyItemOption>> getBySurveyItemId(@PathVariable String surveyItemId) {
        return ResponseEntity.ok(surveyItemOptionService.findBySurveyItemId(surveyItemId));
    }

    @PostMapping
    public ResponseEntity<SurveyItemOption> create(@RequestBody SurveyItemOption option) {
        return ResponseEntity.ok(surveyItemOptionService.createOption(option));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        surveyItemOptionService.deleteOption(id);
        return ResponseEntity.noContent().build();
    }
}