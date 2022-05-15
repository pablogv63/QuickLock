package com.pablogv63.quicklock.ui

import com.pablogv63.quicklock.domain.model.Credential

data class MainState(
    val examplePeople: List<Credential> = emptyList()
)
