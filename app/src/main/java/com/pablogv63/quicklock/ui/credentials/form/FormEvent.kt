package com.pablogv63.quicklock.ui.credentials.form

import androidx.compose.ui.graphics.Color
import com.pablogv63.quicklock.domain.model.Category

sealed class FormEvent {
    data class NameChanged(val name: String): FormEvent()
    data class UsernameChanged(val username: String): FormEvent()
    data class PasswordChanged(val password: String): FormEvent()
    data class RepeatedPasswordChanged(val password: String, val repeatedPassword: String): FormEvent()
    data class ExpirationDateChanged(val expirationDate: String): FormEvent()
    data class CategorySelected(val category: Category): FormEvent()
    data class CategoryRemoved(val category: Category): FormEvent()
    data class NewCategory(val name: String, val color: Color): FormEvent()
    object Submit: FormEvent()
}