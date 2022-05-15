package com.pablogv63.quicklock.data.repository

import com.pablogv63.quicklock.data.data_source.credential.CredentialDao
import com.pablogv63.quicklock.domain.model.Credential
import com.pablogv63.quicklock.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.Flow

class CredentialRepositoryImpl(
    private val dao: CredentialDao
): CredentialRepository {
    override fun getExamplePeople(): Flow<List<Credential>> {
        return dao.getAll()
    }

    override suspend fun getExamplePersonById(id: Int): Flow<Credential> {
        return dao.findById(id)
    }

    override suspend fun insertAll(examplePeople: List<Credential>) {
        examplePeople.map { dao.insert(it) }
    }

    override suspend fun insertExamplePerson(credential: Credential) {
        dao.insert(credential)
    }

    override suspend fun deleteExamplePerson(credential: Credential) {
        dao.delete(credential)
    }
}