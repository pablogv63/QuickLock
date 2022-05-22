package com.pablogv63.quicklock.domain.use_case.form

class ValidatePassword {
    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "Password can't be blank"
            )
        }
        // For the future
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        return ValidationResult(successful = true)
    }
}