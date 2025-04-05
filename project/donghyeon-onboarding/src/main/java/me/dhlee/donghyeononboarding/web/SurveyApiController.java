package me.dhlee.donghyeononboarding.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.dhlee.donghyeononboarding.dto.request.SurveyCreateRequest;
import me.dhlee.donghyeononboarding.dto.response.SurveyCreateResponse;
import me.dhlee.donghyeononboarding.common.ApiResponse;
import me.dhlee.donghyeononboarding.service.SurveyService;

@RequiredArgsConstructor
@RequestMapping("/api/surveys")
@RestController
public class SurveyApiController {

    private final SurveyService surveyService;

    @PostMapping
    public ApiResponse<SurveyCreateResponse> createSurvey(@RequestBody SurveyCreateRequest request) {
        return ApiResponse.ok(surveyService.createSurvey(request));
    }
}
