package com.pablogv63.quicklock.data.repository

import com.pablogv63.quicklock.data.data_source.credential.CredentialDao
import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList
import com.pablogv63.quicklock.domain.model.Credential
import com.pablogv63.quicklock.domain.repository.CredentialRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CredentialRepositoryImpl(
    private val dao: CredentialDao
): CredentialRepository {
    override fun getCredentials(): Flow<List<Credential>> {
        return dao.getAll()
    }

    override fun getCredentialsWithCategories(): Flow<List<CredentialWithCategoryList>> {
        return dao.getAllWithCategories()
    }

    override fun getCredentialById(id: Int): Flow<Credential> {
        return dao.findById(id)
    }

    override suspend fun insertAll(credentialList: List<Credential>) {
        withContext(Dispatchers.IO){
            credentialList.map { dao.insert(it) }
        }
    }

    override suspend fun insertCredential(credential: Credential): Long {
        return withContext(Dispatchers.IO) { dao.insert(credential) }
    }

    override suspend fun deleteCredential(credential: Credential) {
        withContext(Dispatchers.IO) { dao.delete(credential) }
    }

    override suspend fun deleteCredentialFromId(credentialId: Int) {
        withContext(Dispatchers.IO) { dao.delete(credentialId = credentialId) }
    }

}