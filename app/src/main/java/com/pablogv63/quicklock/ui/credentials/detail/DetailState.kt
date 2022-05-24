package com.pablogv63.quicklock.ui.credentials.detail

import com.pablogv63.quicklock.domain.model.Category
import java.time.LocalDate

data class DetailState (
    val credentialId: Int = 0,
    val name: String = "",
    val username: String = "",
    val password: String = "",
    val expirationDate: String = "",
    val lastAccess: String = "",
    val lastModified: String = "",
    val categories: List<Category> = listOf()
)