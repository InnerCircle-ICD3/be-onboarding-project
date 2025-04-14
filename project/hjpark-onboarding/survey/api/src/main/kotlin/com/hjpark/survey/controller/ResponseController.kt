package com.hjpark.survey.controller

import com.hjpark.shared.exception.ExceptionHandler
import com.hjpark.shared.request.ApiRequest
import com.hjpark.shared.response.ApiResponse
import com.hjpark.shared.surveyDto.*
import com.hjpark.survey.ResponseService
import com.hjpark.survey.config.ResponseSwaggerExamples
import com.hjpark.survey.config.SurveySwaggerExamples
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Response", description = "응답 관리 API")
@RestController
@RequestMapping("/api/responses")
class ResponseController(
    private val responseService: ResponseService
) {

    /** 단일 설문에 대한 모든 응답을 조회 */
    @Operation(summary = "[응답 조회]",
        description = """
            특정 설문에 대한 모든 응답을 조회합니다.
            반환되는 데이터에는 각 응답의 정보와 질문별 답변이 포함됩니다.
            
            ---응답데이터 샘플---
            ${ResponseSwaggerExamples.FULL_RESPONSE_EXAMPLE}
            """,
    )
    @GetMapping("/{surveyId}")
    fun getResponses(
        @Parameter(description = "설문 ID", example = "1")
        @PathVariable surveyId: Long
    ): ApiResponse<List<ResponseRetrievalResponse>> {
        return try {
            val responses = responseService.getResponses(surveyId)
            ApiResponse.success(responses)
        } catch (ex: Exception) {
            ExceptionHandler.handleException(ex)
        }
    }

    /** 설문에 대한 응답을 제출 */
    @Operation(summary = "[응답 제출]",
        description = """
            설문에 대한 응답을 제출합니다.
            질문의 유형에 따라 요청 데이터의 형식이 달라집니다.
            
            ${ResponseSwaggerExamples.RESPONSE_TYPES_DESCRIPTION}
            """,
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "응답 제출 요청 데이터",
            content = [
                Content(
                    mediaType = "application/json",
                    examples = [
                        ExampleObject(
                            name = "SubmitResponseExample",
                            value = ResponseSwaggerExamples.RESPONSE_REQUEST_EXAMPLE
                        )
                    ]
                )
            ]
        ),
    )
    @PostMapping
    fun submitResponse(@RequestBody request: SubmitResponseRequest): ApiResponse<ResponseSubmissionResponse> {
        return try {
            val result = responseService.submitResponse(request)
            ApiResponse.success(result)
        } catch (ex: Exception) {
            ExceptionHandler.handleException(ex)
        }
    }
}
