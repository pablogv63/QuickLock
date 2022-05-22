package com.pablogv63.quicklock.domain.use_case.form

import com.pablogv63.quicklock.domain.util.DateParser.toLocalDate
import java.time.LocalDate

class ValidateExpirationDate {

    operator fun invoke(expirationDate: String): ValidationResult {
        val date = expirationDate.toLocalDate()
        // Check if date after now
        date?.let {
            if (date.isBefore(LocalDate.now())){
                return ValidationResult(
                    successful = false,
                    errorMessage = "Date must not be prior to today"
                )
            }
        }
        return ValidationResult(successful = true)
    }
}