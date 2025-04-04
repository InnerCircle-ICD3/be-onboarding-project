package com.chosseang.seonghunonboarding.entity

import com.chosseang.seonghunonboarding.enum.ItemType
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.CollectionTable
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class Item(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "survey_id")
    var survey: Survey? = null,

    var name: String,
    var description: String,

    @Enumerated(EnumType.STRING)
    val type: ItemType,

    @ElementCollection
    @CollectionTable(name = "item_contents", joinColumns = [JoinColumn(name = "itemId")])
    var contents: List<String>
)
