package com.hjpark.survey.controller

import com.hjpark.shared.exception.ExceptionHandler
import com.hjpark.shared.request.ApiRequest
import com.hjpark.shared.response.ApiResponse
import com.hjpark.shared.surveyDto.*
import com.hjpark.survey.SurveyService
import com.hjpark.survey.config.SurveySwaggerExamples
import com.hjpark.survey.entity.Survey
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Survey", description = "설문 관리 API")
@RestController
@RequestMapping("/api/surveys")
class SurveyController(
    private val surveyService: SurveyService
) {

    /** 설문 조회 */
    @Operation(summary = "[설문 조회]",
            description = """
            단일 설문의 모든 정보를 조회합니다. 계층 구조로 표시됩니다.
            반환되는 계층 구조의 형식을 가진 응답값 예시는 아래와 같습니다.
            ${SurveySwaggerExamples.FULL_SURVEYDATA_EXAMPLE}
            """
    )
    @GetMapping("/{surveyId}")
    fun getSurvey(
        @Parameter(description = "설문 ID", example = "1")
        @PathVariable surveyId: Long
    ): ApiResponse<FullSurveyResponse> {
        try {
            val fullSurveyResponse = surveyService.getFullSurvey(surveyId)
            return ApiResponse.success(fullSurveyResponse)
        } catch (ex: Exception) {
            return ExceptionHandler.handleException(ex)
        }
    }

    /** 설문 목록 조회 */
    @Operation(summary = "[설문 목록 조회]")
    @GetMapping
    fun getSurveys(
        @Parameter(description = "검색 키워드", example = "만족도")
        @RequestParam(required = false) keyword: String?
    ): ApiResponse<List<Survey>> {
        try {
            val surveyListDto = surveyService.getSurveys(keyword)
            return ApiResponse.success(surveyListDto, keyword)
        } catch (ex: Exception) {
            return ExceptionHandler.handleException(ex)
        }
    }

    /** 설문 생성 */
    @Operation(
        summary = "[설문 생성]",
        description = """
            새로운 설문을 생성합니다.
            ${SurveySwaggerExamples.QUESTION_TYPES_DESCRIPTION}""",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "설문 생성 요청 데이터",
            content = [
                Content(
                    mediaType = "application/json",
                    examples = [
                        ExampleObject(
                            name = "SurveyRequestExample",
                            value = SurveySwaggerExamples.SURVEY_REQUEST_EXAMPLE
                        )
                    ]
                )
            ]
        )
    )
    @PostMapping
    fun createSurvey(@RequestBody request: ApiRequest<CreateSurveyRequest>): ApiResponse<FullSurveyResponse> {
        try {
            val surveyDto = surveyService.createSurvey(request.data!!)
            return ApiResponse.success(surveyDto, request.requestId)
        } catch (ex: Exception) {
            return ExceptionHandler.handleException(ex)
        }
    }

    /** 설문 수정 */
    @Operation(
        summary = "[설문 수정]",
        description = """
            기존에 존재하는 설문의 구성을 수정합니다.
            ${SurveySwaggerExamples.QUESTION_TYPES_DESCRIPTION}
            ----- 추가 설명 -----
            - 질문 ID (`id`): null이면 새 질문으로 간주됩니다.
            - 옵션 ID (`id`): null이면 새 옵션으로 간주됩니다.
            - 질문 순서 (`sequence`): 질문의 순서를 변경하려면 해당 값을 업데이트하세요.
            - 옵션 순서 (`sequence`): 선택형 질문의 옵션 순서를 변경하려면 해당 값을 업데이트하세요.
            """,
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "설문 수정 요청 데이터",
            content = [
                Content(
                    mediaType = "application/json",
                    examples = [
                        ExampleObject(
                            name = "SurveyRequestExample",
                            value = SurveySwaggerExamples.UPDATE_SURVEY_REQUEST_EXAMPLE
                        )
                    ]
                )
            ]
        )
    )
    @PutMapping("/{surveyId}")
    fun updateSurvey(
        @Parameter(description = "설문 ID", example = "1")
        @PathVariable surveyId: Long,
        @RequestBody request: ApiRequest<UpdateSurveyRequest>
    ): ApiResponse<FullSurveyResponse> {
        try {
            val surveyDto = surveyService.updateSurvey(surveyId, request.data!!)
            return ApiResponse.success(surveyDto, request.requestId)
        } catch (ex: Exception) {
            return ExceptionHandler.handleException(ex)
        }
    }

    /** 설문 삭제 */
    @Operation(summary = "[설문 삭제]", description = "설문 ID를 사용하여 설문을 삭제합니다.")
    @DeleteMapping("/{surveyId}")
    fun deleteSurvey(
        @Parameter(description = "설문 ID", example = "1")
        @PathVariable surveyId: Long
    ): ApiResponse<Unit> {
        try {
            surveyService.deleteSurvey(surveyId)
            return ApiResponse.success()
        } catch (ex: Exception) {
            return ExceptionHandler.handleException(ex)
        }
    }
}