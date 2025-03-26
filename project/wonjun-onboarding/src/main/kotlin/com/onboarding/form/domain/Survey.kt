package com.onboarding.form.domain

import jakarta.persistence.*


@Entity
class Survey (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val description: String,
    @OneToMany(mappedBy = "survey", cascade = [CascadeType.ALL], orphanRemoval = true)
    val item: MutableList<Item> = mutableListOf()
){
    fun addItem(item: Item){
        item.survey = this
        this.item.add(item)
    }
}