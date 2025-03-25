// src/main/kotlin/com/innercircle/survey/domain/Response.kt
package com.innercircle.survey.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "responses")
class Response(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    val survey: Survey,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "response", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<ResponseItem> = mutableListOf()
) {
    // 응답 항목 추가 메서드
    fun addItem(item: ResponseItem) {
        items.add(item)
        item.response = this
    }
}