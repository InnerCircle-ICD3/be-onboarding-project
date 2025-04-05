package com.example.doohyeononboarding.api.controller

import com.example.doohyeononboarding.api.service.SurveyService
import com.example.doohyeononboarding.api.service.request.CreateSurveyRequest
import com.example.doohyeononboarding.api.service.request.UpdateSurveyRequest
import com.example.doohyeononboarding.api.service.response.CreateSurveyResponse
import com.example.doohyeononboarding.api.service.response.UpdateSurveyResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SurveyController(
    private val surveyService: SurveyService,
) {

    /**
     * 설문 등록 API
     *
     * @param request 설문 등록 요청 정보
     *          - title: 설문 제목
     *          - description: 설문 설명
     *          - questions: 설문 항목 리스트
     *              - title: 설문 항목 제목
     *              - description: 설문 항목 설명
     *              - type: 설문 항목 유형 (단답형, 장문형, 단일 선택, 다중 선택)
     *              - isRequired: 항목 필수 여부
     *              - options: 선택형일 경우 선택지 리스트
     *
     * @return 생성된 설문 ID
     */
    @PostMapping("/survey")
    fun createSurvey(@RequestBody request: CreateSurveyRequest): ResponseEntity<CreateSurveyResponse> {
        val response = surveyService.createSurvey(request)
        return ResponseEntity.ok(response)
    }

    /**
     * 설문 수정 API
     *
     * @param request 설문 수정 요청 정보
     *          - title: 설문 제목
     *          - description: 설문 설명
     *          - questions: 설문 항목 리스트
     *              - title: 설문 항목 제목
     *              - description: 설문 항목 설명
     *              - type: 설문 항목 유형 (단답형, 장문형, 단일 선택, 다중 선택)
     *              - isRequired: 항목 필수 여부
     *              - options: 선택형일 경우 선택지 리스트
     *
     * @return 수정된 설문 ID
     */
    @PatchMapping("/survey/{surveyId}")
    fun updateSurvey(
        @PathVariable surveyId: Long,
        @RequestBody request: UpdateSurveyRequest
    ): ResponseEntity<UpdateSurveyResponse> {
        val response = surveyService.updateSurvey(surveyId, request)
        return ResponseEntity.ok(response)
    }

}
