package com.survey.application.ui;

import com.survey.application.dto.request.CreateSurveyRequest;
import com.survey.application.dto.request.ResponseSurveyRequest;
import com.survey.application.dto.request.UpdateSurveyRequest;
import com.survey.application.dto.response.GetAllSurveyResultResponse;
import com.survey.application.service.SurveyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping
    public Long createSurvey(@Valid @RequestBody CreateSurveyRequest request) {
        return surveyService.registerSurvey(request);
    }

    @PutMapping
    public void updateSurvey(@Valid @RequestBody UpdateSurveyRequest request) {
        surveyService.changeSurvey(request);
    }

    @PostMapping("/response")
    public void responseSurvey(@Valid @RequestBody ResponseSurveyRequest request) {
        surveyService.responseSurvey(request);
    }

    @GetMapping("/{survey-id}")
    public List<GetAllSurveyResultResponse> getAllSurveyResponses(@PathVariable("survey-id") Long surveyId) {
        return surveyService.getAllSurveyResponses(surveyId);
    }
}
