package com.pablogv63.composeroomtemplate.ui

import com.pablogv63.composeroomtemplate.domain.model.ExamplePerson

data class MainState(
    val examplePeople: List<ExamplePerson> = emptyList()
)
