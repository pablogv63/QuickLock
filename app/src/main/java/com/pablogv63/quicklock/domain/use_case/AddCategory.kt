package com.pablogv63.quicklock.domain.use_case

import com.pablogv63.quicklock.domain.model.Category
import com.pablogv63.quicklock.domain.model.Credential
import com.pablogv63.quicklock.domain.repository.CategoryRepository
import com.pablogv63.quicklock.domain.repository.CredentialRepository

class AddCategory(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category){
        repository.insertCategory(category)
    }
}