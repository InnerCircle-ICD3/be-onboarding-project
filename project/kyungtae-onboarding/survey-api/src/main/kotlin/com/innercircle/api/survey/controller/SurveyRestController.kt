package com.innercircle.api.survey.controller

import com.innercircle.api.common.response.ApiResponseDto
import com.innercircle.api.survey.controller.request.SurveyAnswerCreateRequest
import com.innercircle.api.survey.controller.request.SurveyCreateRequest
import com.innercircle.api.survey.controller.request.SurveyUpdateRequest
import com.innercircle.api.survey.controller.response.SurveyAnswerCreatedResponse
import com.innercircle.api.survey.controller.response.SurveyCreatedResponse
import com.innercircle.api.survey.controller.response.SurveyResponse
import com.innercircle.api.survey.service.SurveyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.headers.Header
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@Tag(name = "Survey", description = "설문 API")
@RequestMapping("/api/surveys")
@RestController
class SurveyRestController(
    private val surveyService: SurveyService
) {

    @Operation(summary = "설문 생성", description = "새로운 설문을 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "설문 생성 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = SurveyCreatedResponse::class
                        )
                    )
                ],
                headers = [
                    Header(
                        name = "Location",
                        description = "생성된 설문 ID 를 포함한 Get 요청 URI",
                        required = true,
                        schema = Schema(
                            type = "string",
                            format = "uri"
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ApiResponseDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "서버 오류",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ApiResponseDto::class
                        )
                    )
                ]
            )
        ]
    )
    @PostMapping
    fun createSurvey(
        @RequestBody @Valid request: SurveyCreateRequest
    ): ResponseEntity<Any> {
        val surveyCreatedResponse = surveyService.createSurvey(request)
        return ResponseEntity.created(URI.create("/api/surveys/${surveyCreatedResponse.externalId}")).build()
    }

    @Operation(summary = "설문 조회", description = "설문을 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "설문 조회 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = SurveyResponse::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ApiResponseDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "설문을 찾을 수 없음",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ApiResponseDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "서버 오류",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ApiResponseDto::class
                        )
                    )
                ]
            )
        ]
    )
    @GetMapping("/{id:[a-f0-9]{8}-(?:[a-f0-9]{4}-){3}[a-f0-9]{12}}")
    fun getSurvey(
        @PathVariable id: UUID
    ): ResponseEntity<ApiResponseDto<SurveyResponse>> {
        val survey = surveyService.getSurvey(id)
        return ResponseEntity.ok(ApiResponseDto.ok(survey))
    }

    @Operation(summary = "설문 수정", description = "설문을 수정합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "설문 수정 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ApiResponseDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ApiResponseDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "설문을 찾을 수 없음",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ApiResponseDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "서버 오류",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ApiResponseDto::class
                        )
                    )
                ]
            )
        ]
    )
    @PutMapping("/{id:[a-f0-9]{8}-(?:[a-f0-9]{4}-){3}[a-f0-9]{12}}")
    fun updateSurvey(
        @PathVariable id: UUID,
        @RequestBody @Valid request: SurveyUpdateRequest
    ): ResponseEntity<Any> {
        surveyService.updateSurvey(id, request)
        return ResponseEntity.noContent().build()
    }


    @Operation(summary = "설문 답변", description = "설문에 답변합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "설문 답변 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = SurveyAnswerCreatedResponse::class
                        )
                    )
                ],
                headers = [
                    Header(
                        name = "Location",
                        description = "생성된 설문 답변 ID를 포함한 Get 요청 URI",
                        required = true,
                        schema = Schema(
                            type = "string",
                            format = "uri"
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ApiResponseDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "설문을 찾을 수 없음",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ApiResponseDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "서버 오류",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ApiResponseDto::class
                        )
                    )
                ]
            )
        ]
    )
    @PostMapping("/{surveyId:[a-f0-9]{8}-(?:[a-f0-9]{4}-){3}[a-f0-9]{12}}/answers")
    fun answerSurvey(
        @PathVariable surveyId: UUID,
        @RequestBody @Valid request: SurveyAnswerCreateRequest
    ): ResponseEntity<SurveyAnswerCreatedResponse> {
        val surveyAnswerCreatedResponse = surveyService.answerSurvey(surveyId, request)
        return ResponseEntity.created(
            URI.create(
                "/api/surveys/${surveyId}" +
                        "/answers/${surveyAnswerCreatedResponse.id}"
            )
        ).build()
    }
}