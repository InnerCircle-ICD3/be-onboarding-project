package com.chosseang.seonghunonboarding.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class Survey(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String?,
    val description: String,

    @OneToMany(mappedBy = "survey", orphanRemoval = true, cascade = [CascadeType.ALL])
    var items: MutableList<Item>
){
    // 아이템 추가 메서드 - 양방향 관계 유지
    fun addItem(item: Item) {
        items.add(item)
        item.survey = this
    }

    // 여러 아이템 한번에 추가
    fun addItems(itemList: List<Item>) {
        itemList.forEach { addItem(it) }
    }
}
