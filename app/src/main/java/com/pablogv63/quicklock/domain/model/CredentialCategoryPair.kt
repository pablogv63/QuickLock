package com.pablogv63.quicklock.domain.model

import androidx.room.Entity

@Entity(primaryKeys = ["credentialId", "categoryId"])
data class CredentialCategoryPair(
    val credentialId: Int,
    val categoryId: Int
)