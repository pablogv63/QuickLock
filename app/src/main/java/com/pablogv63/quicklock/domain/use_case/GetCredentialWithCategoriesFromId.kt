package com.pablogv63.quicklock.domain.use_case

import com.pablogv63.quicklock.domain.model.Category
import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList
import com.pablogv63.quicklock.domain.repository.CategoryRepository
import com.pablogv63.quicklock.domain.repository.CredentialCategoryPairRepository
import com.pablogv63.quicklock.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.Flow

class GetCredentialWithCategoriesFromId(
    private val pairRepository: CredentialCategoryPairRepository
) {
    operator fun invoke(id: Int): Flow<CredentialWithCategoryList?>{
        return pairRepository.getCredentialWithCategoriesFromId(id)
    }
}