package com.pablogv63.quicklock.domain.use_case

import com.pablogv63.quicklock.domain.model.Credential
import com.pablogv63.quicklock.domain.model.CredentialCategoryPair
import com.pablogv63.quicklock.domain.repository.CredentialCategoryPairRepository
import com.pablogv63.quicklock.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull

class DeleteCredential(
    private val repository: CredentialRepository,
    private val pairRepository: CredentialCategoryPairRepository
) {
    /**
     * Deletes the credential and all pairs that have its id
     */
    suspend operator fun invoke(credential: Credential){
        val credentialId = credential.credentialId
        invoke(credentialId)
    }

    /**
     * Deletes the credential and all pairs that have its id
     */
    suspend operator fun invoke(credentialId: Int) {
        repository.deleteCredentialFromId(credentialId = credentialId)
        pairRepository.deleteCredentialWithCategoriesFromId(credentialId = credentialId)
    }
}