package com.pablogv63.quicklock.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category (
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int = -1,
    val name: String,
    val colour: Int
)