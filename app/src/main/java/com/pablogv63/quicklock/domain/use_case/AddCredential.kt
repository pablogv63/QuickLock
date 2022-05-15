package com.pablogv63.quicklock.domain.use_case

import com.pablogv63.quicklock.domain.model.Credential
import com.pablogv63.quicklock.domain.repository.CredentialRepository

class AddCredential(
    private val repository: CredentialRepository
) {
    suspend operator fun invoke(credential: Credential){
        repository.insertExamplePerson(credential)
    }
}