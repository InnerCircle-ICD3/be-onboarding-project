package com.innercircle.api.survey.controller

import com.innercircle.api.common.jsonMapper
import com.innercircle.api.survey.controller.request.SurveyCreateRequest
import com.innercircle.api.survey.controller.request.SurveyUpdateRequest
import com.innercircle.api.survey.controller.response.SurveyResponse
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.ValidatableResponse
import org.apache.http.HttpHeaders
import org.apache.http.HttpStatus

val LOCATION_HEADER_VALUE_REGEX = "/api/surveys/[a-f0-9]{8}-([a-f0-9]{4}-){3}[a-f0-9]{12}"
fun getIdFromLocation(locationHeaderValue: String?) = locationHeaderValue!!.substringAfterLast("/")

fun 설문_생성(surveyCreateRequest: SurveyCreateRequest): String? =
    RestAssured.given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body(jsonMapper().writeValueAsString(surveyCreateRequest))
        .post("/api/surveys")
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .extract()
        .headers()
        .get(HttpHeaders.LOCATION)
        .value

fun 설문_조회(surveyId: String): SurveyResponse =
    RestAssured.given()
        .accept(ContentType.JSON)
        .get("/api/surveys/$surveyId")
        .then()
        .log().body()
        .statusCode(HttpStatus.SC_OK)
        .extract()
        .jsonPath()
        .getObject("data", SurveyResponse::class.java)

fun 설문_수정(id: String, surveyUpdateRequest: SurveyUpdateRequest): ValidatableResponse? =
    RestAssured.given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body(jsonMapper().writeValueAsString(surveyUpdateRequest))
        .put("/api/surveys/$id")
        .then()
        .log().body()
        .statusCode(HttpStatus.SC_NO_CONTENT)