package com.pablogv63.quicklock.domain.use_case

import com.pablogv63.quicklock.domain.model.Credential
import com.pablogv63.quicklock.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.Flow

class GetCredentials(
    private val repository: CredentialRepository
) {

    operator fun invoke(): Flow<List<Credential>> {
        return repository.getExamplePeople()
    }
}