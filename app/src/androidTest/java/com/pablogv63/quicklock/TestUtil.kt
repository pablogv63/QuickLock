package com.pablogv63.quicklock

import com.pablogv63.quicklock.domain.model.Category
import com.pablogv63.quicklock.domain.model.Credential
import java.time.LocalDate

object TestUtil {
    fun createCredential(
        credentialId: Int,
        name: String,
        username: String,
        password: String
    ): Credential{
        return Credential(
            credentialId = credentialId,
            name = name,
            username = username,
            password = password,
            lastAccess = LocalDate.now(),
            lastModified = LocalDate.now(),
            expirationDate = null
        )
    }

    fun createCategory(
        categoryId: Int,
        name: String,
        colour: Int
    ): Category {
        return Category(
            categoryId = categoryId,
            name = name,
            colour = colour
        )
    }

    fun createCredentialWithCategories(

    ){}
}