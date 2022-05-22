package com.pablogv63.quicklock.ui.credentials.add

sealed class AddFormEvent {
    data class NameChanged(val name: String): AddFormEvent()
    data class UsernameChanged(val username: String): AddFormEvent()
    data class PasswordChanged(val password: String): AddFormEvent()
    data class RepeatedPasswordChanged(val password: String, val repeatedPassword: String): AddFormEvent()
    data class ExpirationDateChanged(val expirationDate: String): AddFormEvent()
    data class CategoryChanged(val category: String): AddFormEvent()
    object Submit: AddFormEvent()
}