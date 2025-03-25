package com.survey.application.ui;

import com.survey.application.dto.request.CreateSurveyRequest;
import com.survey.application.dto.request.UpdateSurveyRequest;
import com.survey.application.service.SurveyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping
    public void createSurvey(@Valid @RequestBody CreateSurveyRequest request) {
        surveyService.registerSurvey(request);
    }

    @PutMapping
    public void updateSurvey(@Valid @RequestBody UpdateSurveyRequest request) {
        surveyService.changeSurvey(request);
    }
}
