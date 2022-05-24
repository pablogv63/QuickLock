package com.pablogv63.quicklock.domain.use_case

import com.pablogv63.quicklock.domain.model.Credential
import com.pablogv63.quicklock.domain.model.CredentialCategoryPair
import com.pablogv63.quicklock.domain.repository.CredentialCategoryPairRepository
import com.pablogv63.quicklock.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.collectLatest

class DeleteCredential(
    private val repository: CredentialRepository,
    private val pairRepository: CredentialCategoryPairRepository
) {
    /**
     * Deletes the credential and all pairs that have its id
     */
    suspend operator fun invoke(credential: Credential){
        repository.deleteCredential(credential)
        val credentialWithCategoryListFlow = pairRepository.getCredentialWithCategoriesFromId(
        credentialId = credential.credentialId)
        credentialWithCategoryListFlow.collectLatest {
            it.categories.map { category ->
                pairRepository.deleteCredentialCategoryPair(
                    CredentialCategoryPair(
                        credentialId = credential.credentialId,
                        categoryId = category.categoryId
                    )
                )
            }
        }
    }

    /**
     * Deletes the credential and all pairs that have its id
     */
    suspend operator fun invoke(credentialId: Int) {
        repository.deleteCredentialFromId(credentialId = credentialId)
        val credentialWithCategoryListFlow = pairRepository.getCredentialWithCategoriesFromId(
            credentialId = credentialId)
        credentialWithCategoryListFlow.collectLatest {
            it.categories.map { category ->
                pairRepository.deleteCredentialCategoryPair(
                    CredentialCategoryPair(
                        credentialId = credentialId,
                        categoryId = category.categoryId
                    )
                )
            }
        }
    }
}