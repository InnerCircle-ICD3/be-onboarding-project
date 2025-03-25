package entity

import jakarta.persistence.CollectionTable
import jakarta.persistence.ElementCollection
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

data class Answer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val name: String,

    @ElementCollection
    @CollectionTable(name = "answer_items", joinColumns = [JoinColumn(name = "answer_id")])
    val items: List<String>,

    @ElementCollection
    @CollectionTable( name = "answer_responses", joinColumns = [JoinColumn(name = "answer_id")])
    val responses: List<String>,

    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: Survey
)
