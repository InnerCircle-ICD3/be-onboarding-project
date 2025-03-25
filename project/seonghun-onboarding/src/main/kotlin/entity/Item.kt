package entity

import enum.ItemType
import jakarta.persistence.CollectionTable
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn

@Entity
data class Item(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val description: String,
    val type: Enum<ItemType>,

    @ElementCollection
    @CollectionTable(name = "item_contents", joinColumns = [JoinColumn(name = "itemId")])
    val contents: List<String>
)
