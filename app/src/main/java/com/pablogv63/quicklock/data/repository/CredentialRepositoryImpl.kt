package com.pablogv63.quicklock.data.repository

import com.pablogv63.quicklock.data.data_source.credential.CredentialDao
import com.pablogv63.quicklock.data.data_source.credential_category_pair.CredentialWithCategoryList
import com.pablogv63.quicklock.domain.model.Credential
import com.pablogv63.quicklock.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.Flow

class CredentialRepositoryImpl(
    private val dao: CredentialDao
): CredentialRepository {
    override fun getCredentials(): Flow<List<Credential>> {
        return dao.getAll()
    }

    override fun getCredentialsWithCategories(): Flow<List<CredentialWithCategoryList>> {
        return dao.getAllWithCategories()
    }

    override suspend fun getCredentialById(id: Int): Flow<Credential> {
        return dao.findById(id)
    }

    override suspend fun insertAll(credentialList: List<Credential>) {
        credentialList.map { dao.insert(it) }
    }

    override suspend fun insertCredential(credential: Credential) {
        dao.insert(credential)
    }

    override suspend fun deleteCredential(credential: Credential) {
        dao.delete(credential)
    }

}