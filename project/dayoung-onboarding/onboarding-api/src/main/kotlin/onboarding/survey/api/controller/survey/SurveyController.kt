package onboarding.survey.api.controller.survey

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import onboarding.survey.api.model.request.AnswerSurveyRequest
import onboarding.survey.api.model.request.CreateSurveyRequest
import onboarding.survey.api.model.request.UpdateSurveyRequest
import onboarding.survey.api.model.response.AnswerSurveyResponse
import onboarding.survey.api.model.response.CreateSurveyResponse
import onboarding.survey.api.model.response.GetAnswersResponse
import onboarding.survey.api.model.response.UpdateSurveyResponse
import onboarding.survey.api.service.survey.SurveyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Survey", description = "설문조사 관련 CRUD API")
@RestController
class SurveyController(
    private val surveyService: SurveyService  // ✅ 생성자 주입
) {
    @Operation(summary = "설문조사 생성")
    @PostMapping("/survey")
    fun createSurvey(@RequestBody request: CreateSurveyRequest): ResponseEntity<CreateSurveyResponse> {
        val response = surveyService.createSurvey(request)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "설문조사 수정")
    @PutMapping("/survey/{surveyId}")
    fun updateSurvey(
        @PathVariable surveyId: Int,
        @RequestBody request: UpdateSurveyRequest
    ): ResponseEntity<UpdateSurveyResponse> {
        val response = surveyService.updateSurvey(surveyId, request)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "설문 응답 제출")
    @PostMapping("/survey/answer/{surveyId}")
    fun answerSurvey(
        @PathVariable surveyId: Int,
        @RequestBody request: AnswerSurveyRequest
    ): ResponseEntity<AnswerSurveyResponse> {
        // TODO: 서비스 호출 및 저장 처리
        return ResponseEntity.ok(AnswerSurveyResponse(surveyId = surveyId, answerId = 1))
    }

    @Operation(summary = "설문 응답 전체 조회")
    @GetMapping("/survey/answer/{surveyId}")
    fun getAnswers(
        @PathVariable surveyId: Int,
        @RequestParam(required = false) filters: Map<String, String>?
    ): ResponseEntity<GetAnswersResponse> {
        // TODO: 서비스 호출 및 필터링 처리
        return ResponseEntity.ok(GetAnswersResponse(answers = emptyList()))
    }
}