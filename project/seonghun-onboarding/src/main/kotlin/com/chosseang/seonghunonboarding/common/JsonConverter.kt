package com.chosseang.seonghunonboarding.common

import com.chosseang.seonghunonboarding.dto.QuestionItem
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class JsonConverter {

    private val objectMapper = ObjectMapper()

    /**
     * 질문 항목 맵을 JSON 문자열로 변환
     */
    fun convertItemsToJson(items: Map<String, QuestionItem>): String {
        try {
            return objectMapper.writeValueAsString(items)
        } catch (e: JsonProcessingException) {
            throw IllegalArgumentException("Error converting items to JSON", e)
        }
    }

    /**
     * JSON 문자열을 질문 항목 맵으로 변환
     */
    fun convertJsonToItems(json: String): Map<String, QuestionItem> {
        if (json.isBlank()) return emptyMap()

        try {
            return objectMapper.readValue(json, object : TypeReference<Map<String, QuestionItem>>() {})
        } catch (e: JsonProcessingException) {
            throw IllegalArgumentException("Error converting JSON to items", e)
        }
    }

    /**
     * 응답 맵을 JSON 문자열로 변환
     */
    fun convertResponsesToJson(responses: Map<String, List<String>>): String {
        try {
            return objectMapper.writeValueAsString(responses)
        } catch (e: JsonProcessingException) {
            throw IllegalArgumentException("Error converting responses to JSON", e)
        }
    }

    /**
     * JSON 문자열을 응답 맵으로 변환
     */
    fun convertJsonToResponses(json: String): Map<String, List<String>> {
        if (json.isBlank()) return emptyMap()

        try {
            return objectMapper.readValue(json, object : TypeReference<Map<String, List<String>>>() {})
        } catch (e: JsonProcessingException) {
            throw IllegalArgumentException("Error converting JSON to responses", e)
        }
    }
}
