package com.pablogv63.quicklock.domain.use_case.form

class ValidateName {

    operator fun invoke(name: String): ValidationResult {
        if (name.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "Name can't be blank"
            )
        }
        return ValidationResult(successful = true)
    }
}