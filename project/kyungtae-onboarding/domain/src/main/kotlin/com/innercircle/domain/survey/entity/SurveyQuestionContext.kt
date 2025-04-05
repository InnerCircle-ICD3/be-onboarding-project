package com.innercircle.domain.survey.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class SurveyQuestionContext(

    @Column(nullable = false, length = 100)
    var name: String,

    @Column(nullable = false, length = 500)
    var description: String,
) {
    init {
        require(name.isNotBlank()) { "name must not be blank" }
        require(description.isNotBlank()) { "description must not be blank" }
    }

}