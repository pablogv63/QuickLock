package com.pablogv63.quicklock.domain.repository

import com.pablogv63.quicklock.domain.model.CredentialCategoryPair
import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList
import kotlinx.coroutines.flow.Flow

interface CredentialCategoryPairRepository {

    suspend fun getAll(): Flow<List<CredentialCategoryPair>>
    suspend fun getCredentialsWithCategories(): Flow<List<CredentialWithCategoryList>>
    suspend fun insertAll(credentialCategoryPairs: List<CredentialCategoryPair>)
    suspend fun insertCredentialCategoryPair(credentialCategoryPair: CredentialCategoryPair)
    suspend fun deleteCredentialCategoryPair(credentialCategoryPair: CredentialCategoryPair)
}