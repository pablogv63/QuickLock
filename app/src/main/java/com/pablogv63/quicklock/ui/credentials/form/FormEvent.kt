package com.pablogv63.quicklock.ui.credentials.form

sealed class FormEvent {
    data class NameChanged(val name: String): FormEvent()
    data class UsernameChanged(val username: String): FormEvent()
    data class PasswordChanged(val password: String): FormEvent()
    data class RepeatedPasswordChanged(val password: String, val repeatedPassword: String): FormEvent()
    data class ExpirationDateChanged(val expirationDate: String): FormEvent()
    data class CategoryChanged(val category: String): FormEvent()
    object Submit: FormEvent()
}