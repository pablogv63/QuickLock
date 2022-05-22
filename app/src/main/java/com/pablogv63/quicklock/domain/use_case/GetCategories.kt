package com.pablogv63.quicklock.domain.use_case

import com.pablogv63.quicklock.domain.model.Category
import com.pablogv63.quicklock.domain.repository.CategoryRepository
import com.pablogv63.quicklock.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.Flow

class GetCategories(
    private val repository: CategoryRepository
) {
    operator fun invoke(): Flow<List<Category>>{
        return repository.getCategories()
    }
}