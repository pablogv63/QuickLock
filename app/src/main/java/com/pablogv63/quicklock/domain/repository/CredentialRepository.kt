package com.pablogv63.quicklock.domain.repository

import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList
import com.pablogv63.quicklock.domain.model.Credential
import kotlinx.coroutines.flow.Flow

interface CredentialRepository {

    fun getCredentials(): Flow<List<Credential>>
    fun getCredentialsWithCategories(): Flow<List<CredentialWithCategoryList>>
    suspend fun getCredentialById(id: Int): Flow<Credential>
    suspend fun insertAll(credentialList: List<Credential>)
    suspend fun insertCredential(credential: Credential): Long
    suspend fun deleteCredential(credential: Credential)
}