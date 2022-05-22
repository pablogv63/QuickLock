package com.pablogv63.quicklock.domain.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.pablogv63.quicklock.domain.model.Category
import com.pablogv63.quicklock.domain.model.Credential
import com.pablogv63.quicklock.domain.model.CredentialCategoryPair

data class CredentialWithCategoryList(
    @Embedded val credential: Credential,
    @Relation(
        parentColumn = "credentialId",
        entityColumn = "categoryId",
        associateBy = Junction(CredentialCategoryPair::class)
    )
    val categories: List<Category>
)
