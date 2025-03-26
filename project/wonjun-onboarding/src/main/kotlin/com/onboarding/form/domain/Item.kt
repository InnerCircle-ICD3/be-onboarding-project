package com.onboarding.form.domain

import jakarta.persistence.*

@Entity
class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val description: String,
    @Enumerated(EnumType.STRING)
    val type: ItemType,
    val isRequired: Boolean,
    @ElementCollection
    @CollectionTable
    val options: List<String>?,
    @ManyToOne
    @JoinColumn(name = "SURVEY_ID")
    var survey: Survey? = null
){

}
