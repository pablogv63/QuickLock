package com.pablogv63.composeroomtemplate

import com.pablogv63.composeroomtemplate.domain.model.ExamplePerson
import java.time.LocalDate

object TestUtil {
    fun createExamplePerson(name: String, birthdate: LocalDate): ExamplePerson{
        return ExamplePerson(
            id = 1,
            name = name,
            birthdate = birthdate
        )
    }
}