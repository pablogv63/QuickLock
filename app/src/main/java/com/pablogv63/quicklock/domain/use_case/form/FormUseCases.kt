package com.pablogv63.quicklock.domain.use_case.form

data class FormUseCases(
    val validateName: ValidateName,
    val validateUsername: ValidateUsername,
    val validatePassword: ValidatePassword,
    val validateRepeatedPassword: ValidateRepeatedPassword,
    val validateExpirationDate: ValidateExpirationDate,
    val validateCategory: ValidateCategory
)