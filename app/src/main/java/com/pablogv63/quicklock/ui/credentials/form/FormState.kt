package com.pablogv63.quicklock.ui.credentials.form

import com.pablogv63.quicklock.domain.model.Category
import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList

data class FormState(
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
    val category: String = "",
    val categoryError: String? = null,
    val categories: List<Category> = emptyList(),
    val selectedCategories: List<Category> = emptyList(),
    val originalPassword: String = "",
    val passwordTextChanged: Boolean = false,
)