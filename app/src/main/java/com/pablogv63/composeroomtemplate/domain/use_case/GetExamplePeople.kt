package com.pablogv63.composeroomtemplate.domain.use_case

import com.pablogv63.composeroomtemplate.domain.model.ExamplePerson
import com.pablogv63.composeroomtemplate.domain.repository.ExamplePersonRepository
import kotlinx.coroutines.flow.Flow

class GetExamplePeople(
    private val repository: ExamplePersonRepository
) {

    operator fun invoke(): Flow<List<ExamplePerson>> {
        return repository.getExamplePeople()
    }
}