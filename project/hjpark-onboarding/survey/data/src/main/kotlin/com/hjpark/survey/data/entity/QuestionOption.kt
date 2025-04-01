package com.hjpark.survey.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "question_option")
class QuestionOption(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    var question: Question,

    @Column(name = "option_text", nullable = false)
    var text: String,

    @Column(name = "sequence", nullable = false)
    var sequence: Int,

    @OneToMany(mappedBy = "option")
    val responseItems: MutableList<ResponseItem> = mutableListOf()
) 