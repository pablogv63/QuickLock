package com.pablogv63.quicklock.ui.credentials.add

import com.pablogv63.quicklock.domain.model.Category

data class AddState(
    val name: String = "",
    val nameError: String? = null,
    val username: String = "",
    val usernameError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,
    val expirationDate: String = "",
    val expirationDateError: String? = null,
    val categories: List<Category> = emptyList(),
    val category: String = "",
    val categoryError: String? = null,
    val categoryDropdownMenuExpanded: Boolean = false
)