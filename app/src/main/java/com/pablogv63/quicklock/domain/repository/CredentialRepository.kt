package com.pablogv63.quicklock.domain.repository

import com.pablogv63.quicklock.domain.model.Credential
import kotlinx.coroutines.flow.Flow

interface CredentialRepository {

    fun getExamplePeople(): Flow<List<Credential>>
    suspend fun getExamplePersonById(id: Int): Flow<Credential>
    suspend fun insertAll(examplePeople: List<Credential>)
    suspend fun insertExamplePerson(credential: Credential)
    suspend fun deleteExamplePerson(credential: Credential)
}