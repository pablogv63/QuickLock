package com.pablogv63.quicklock.domain.use_case.form

import com.pablogv63.quicklock.domain.model.Category

/**
 * Validates the category
 * (for now, can't create a new category)
 */
class ValidateCategory {

    operator fun invoke(categoryName: String, list: List<Category>): ValidationResult {
        if (categoryName.isNotBlank() and !list.any { categoryName == it.name }){
            return ValidationResult(
                successful = false,
                errorMessage = "Category doesn't exist"
            )
        }
        return ValidationResult(successful = true)
    }
}