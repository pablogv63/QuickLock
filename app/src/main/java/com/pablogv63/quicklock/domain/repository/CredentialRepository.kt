package com.pablogv63.quicklock.domain.repository

import com.pablogv63.quicklock.data.data_source.credential_category_pair.CredentialWithCategoryList
import com.pablogv63.quicklock.domain.model.Credential
import kotlinx.coroutines.flow.Flow

interface CredentialRepository {

    fun getCredentials(): Flow<List<Credential>>
    fun getCredentialsWithCategories(): Flow<List<CredentialWithCategoryList>>
    suspend fun getCredentialById(id: Int): Flow<Credential>
    suspend fun insertAll(credentialList: List<Credential>)
    suspend fun insertCredential(credential: Credential)
    suspend fun deleteCredential(credential: Credential)
}