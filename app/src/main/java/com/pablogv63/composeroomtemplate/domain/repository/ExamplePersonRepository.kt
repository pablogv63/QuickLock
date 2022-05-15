package com.pablogv63.composeroomtemplate.domain.repository

import com.pablogv63.composeroomtemplate.domain.model.ExamplePerson
import kotlinx.coroutines.flow.Flow

interface ExamplePersonRepository {

    fun getExamplePeople(): Flow<List<ExamplePerson>>
    suspend fun getExamplePersonById(id: Int): Flow<ExamplePerson>
    suspend fun insertAll(examplePeople: List<ExamplePerson>)
    suspend fun insertExamplePerson(examplePerson: ExamplePerson)
    suspend fun deleteExamplePerson(examplePerson: ExamplePerson)
}