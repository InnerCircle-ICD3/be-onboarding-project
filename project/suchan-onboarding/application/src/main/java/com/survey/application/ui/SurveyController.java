package com.survey.application.ui;

import com.survey.application.dto.CreateSurveyRequest;
import com.survey.application.service.SurveyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping
    public void createSurvey(@RequestBody CreateSurveyRequest request) {
        surveyService.createSurvey(request);
    }
}
