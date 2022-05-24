package com.pablogv63.quicklock.domain.use_case

import com.pablogv63.quicklock.domain.model.CredentialCategoryPair
import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList
import com.pablogv63.quicklock.domain.repository.CategoryRepository
import com.pablogv63.quicklock.domain.repository.CredentialCategoryPairRepository
import com.pablogv63.quicklock.domain.repository.CredentialRepository

class EditCredentialWithCategories(
    private val credentialRepository: CredentialRepository,
    private val categoryRepository: CategoryRepository,
    private val credentialCategoryPairRepository: CredentialCategoryPairRepository
) {
    suspend operator fun invoke(credentialWithCategoryList: CredentialWithCategoryList){

        // Update credential
        val credential = credentialWithCategoryList.credential
        val newCredentialId = credentialRepository.insertCredential(credential)
        // Insert categories
        val categoryList = credentialWithCategoryList.categories
        categoryRepository.insertAll(categoryList)
        // Update credential category pairs
        categoryList.map { category ->
            val pair = CredentialCategoryPair(
                credentialId = newCredentialId.toInt(),
                categoryId = category.categoryId
            )
            credentialCategoryPairRepository.insertCredentialCategoryPair(pair)
        }
    }
}