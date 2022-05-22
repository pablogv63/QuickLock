package com.pablogv63.quicklock.domain.use_case.form

class ValidateUsername {
    operator fun invoke(username: String): ValidationResult {
        if (username.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "Username can't be blank"
            )
        }
        return ValidationResult(successful = true)
    }
}