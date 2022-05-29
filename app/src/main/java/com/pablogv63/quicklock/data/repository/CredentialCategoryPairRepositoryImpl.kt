package com.pablogv63.quicklock.data.repository

import com.pablogv63.quicklock.data.data_source.credential_category_pair.CredentialCategoryPairDao
import com.pablogv63.quicklock.domain.model.CredentialCategoryPair
import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList
import com.pablogv63.quicklock.domain.repository.CredentialCategoryPairRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CredentialCategoryPairRepositoryImpl(
    private val dao: CredentialCategoryPairDao
): CredentialCategoryPairRepository {
    override fun getAll(): Flow<List<CredentialCategoryPair>> {
        return dao.getCredentialCategoryPairs()
    }

    override fun getAllFromId(credentialId: Int): Flow<List<CredentialCategoryPair>> {
        return dao.getCredentialCategoryPairsFromId(credentialId)
    }

    override fun getCredentialsWithCategories(): Flow<List<CredentialWithCategoryList>> {
        return dao.getCredentialsWithCategories()
    }

    override fun getCredentialWithCategoriesFromId(credentialId: Int): Flow<CredentialWithCategoryList?> {
        return dao.getCredentialWithCategoriesFromId(credentialId)
    }

    override suspend fun insertAll(credentialCategoryPairs: List<CredentialCategoryPair>) {
        credentialCategoryPairs.map { dao.insert(it) }
    }

    override suspend fun insertCredentialCategoryPair(credentialCategoryPair: CredentialCategoryPair) {
        withContext(Dispatchers.IO) { dao.insert(credentialCategoryPair) }
    }

    override suspend fun deleteCredentialCategoryPair(credentialCategoryPair: CredentialCategoryPair) {
        withContext(Dispatchers.IO) { dao.delete(credentialCategoryPair) }
    }

    override suspend fun deleteCredentialWithCategoriesFromId(credentialId: Int) {
        withContext(Dispatchers.IO) { dao.deleteCredentialCategoryPairsFromId(credentialId) }
    }
}