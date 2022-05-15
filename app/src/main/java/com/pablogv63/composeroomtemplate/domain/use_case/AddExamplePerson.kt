package com.pablogv63.composeroomtemplate.domain.use_case

import com.pablogv63.composeroomtemplate.domain.model.ExamplePerson
import com.pablogv63.composeroomtemplate.domain.repository.ExamplePersonRepository

class AddExamplePerson(
    private val repository: ExamplePersonRepository
) {
    suspend operator fun invoke(examplePerson: ExamplePerson){
        repository.insertExamplePerson(examplePerson)
    }
}