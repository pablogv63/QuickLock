package com.pablogv63.quicklock.domain.use_case.form

class ValidateRepeatedPassword {
    operator fun invoke(password: String, repeatedPassword: String): ValidationResult {
        if (repeatedPassword.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "Password can't be blank"
            )
        }
        if (password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "Passwords don't match"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}