package com.pablogv63.composeroomtemplate.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablogv63.composeroomtemplate.domain.model.ExamplePerson
import com.pablogv63.composeroomtemplate.domain.use_case.ExamplePersonUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainViewModel(
    private val examplePersonUseCases: ExamplePersonUseCases
): ViewModel() {

    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state

    private var getExamplePeopleJob: Job? = null

    init {
        addExamplePeople() // Remove call
        getExamplePeople()
    }

    /**
     * Function to add sample data
     * TODO: Replace with proper data insertion
     * (view with form for example)
     */
    private fun addExamplePeople(){
        viewModelScope.launch(Dispatchers.IO){
            val examplePerson1 = ExamplePerson(
                id = 1,
                name = "Example person 1",
                birthdate = LocalDate.of(2020,2,15)
            )
            examplePersonUseCases.addExamplePerson(examplePerson1)
            val examplePerson2 = ExamplePerson(
                id = 2,
                name = "Example person 2",
                birthdate = LocalDate.of(2021,3,20)
            )
            examplePersonUseCases.addExamplePerson(examplePerson2)
            val examplePerson3 = ExamplePerson(
                id = 3,
                name = "Example person 3",
                birthdate = LocalDate.now()
            )
            examplePersonUseCases.addExamplePerson(examplePerson3)
        }
    }

    private fun getExamplePeople(){
        getExamplePeopleJob?.cancel()
        getExamplePeopleJob = examplePersonUseCases.getExamplePeople()
            .onEach { examplePeople ->
                _state.value = state.value.copy(
                    examplePeople = examplePeople
                )
            }
            .launchIn(viewModelScope)
    }

}