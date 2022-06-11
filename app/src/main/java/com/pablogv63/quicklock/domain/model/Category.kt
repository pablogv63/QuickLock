package com.pablogv63.quicklock.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category (
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int = 0,
    val name: String,
    val colour: Int
){
    fun toValuesList() = listOf(
        categoryId.toString(),
        name,
        colour.toString()
    )

    companion object{
        fun variableNamesList() = listOf(
            "[categoryId]", "[name]", "[colour]"
        )
    }
}