package com.pablogv63.quicklock.domain.use_case.form

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
