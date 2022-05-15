package com.pablogv63.composeroomtemplate.data.repository

import com.pablogv63.composeroomtemplate.data.data_source.example_person.ExamplePersonDao
import com.pablogv63.composeroomtemplate.domain.model.ExamplePerson
import com.pablogv63.composeroomtemplate.domain.repository.ExamplePersonRepository
import kotlinx.coroutines.flow.Flow

class ExamplePersonRepositoryImpl(
    private val dao: ExamplePersonDao
): ExamplePersonRepository {
    override fun getExamplePeople(): Flow<List<ExamplePerson>> {
        return dao.getAll()
    }

    override suspend fun getExamplePersonById(id: Int): Flow<ExamplePerson> {
        return dao.findById(id)
    }

    override suspend fun insertAll(examplePeople: List<ExamplePerson>) {
        examplePeople.map { dao.insert(it) }
    }

    override suspend fun insertExamplePerson(examplePerson: ExamplePerson) {
        dao.insert(examplePerson)
    }

    override suspend fun deleteExamplePerson(examplePerson: ExamplePerson) {
        dao.delete(examplePerson)
    }
}