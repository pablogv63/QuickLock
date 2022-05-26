package com.pablogv63.quicklock.ui.generator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pablogv63.quicklock.domain.use_case.generator.GeneratorUseCases

class GeneratorViewModel(
    private val generatorUseCases: GeneratorUseCases
): ViewModel() {

    var generatorState by mutableStateOf(GeneratorState())

    init {
        generatePasswordFromState()
    }

    fun onEvent(generatorEvent: GeneratorEvent) {
        when (generatorEvent) {
            is GeneratorEvent.LengthChanged -> {
                generatorState = generatorState.copy(
                    length = generatorEvent.length
                )
                generatePasswordFromState()
            }
            is GeneratorEvent.UppercaseToggleChanged -> {
                generatorState = generatorState.copy(
                    uppercase = generatorEvent.uppercaseToggle
                )
                generatePasswordFromState()
            }
            is GeneratorEvent.LowercaseToggleChanged -> {
                generatorState = generatorState.copy(
                    lowercase = generatorEvent.lowercaseToggle
                )
                generatePasswordFromState()
            }
            is GeneratorEvent.NumbersToggleChanged -> {
                generatorState = generatorState.copy(
                    numbers = generatorEvent.numbersToggle
                )
                generatePasswordFromState()
            }
            is GeneratorEvent.SpecialCharsToggleChanged -> {
                generatorState = generatorState.copy(
                    special = generatorEvent.specialCharsToggle
                )
                generatePasswordFromState()
            }
            is GeneratorEvent.RefreshPassword -> { generatePasswordFromState() }
            is GeneratorEvent.Submit -> {

            }
        }
    }

    private fun generatePasswordFromState(){
        val generatedPassword = generatorUseCases.generatePassword(
            length = generatorState.length,
            includeUpperCase = generatorState.uppercase,
            includeLowerCase = generatorState.lowercase,
            includeNumbers = generatorState.numbers,
            includeSpecial = generatorState.special
        )
        generatorState = generatorState.copy(
            generatedPassword = generatedPassword
        )
    }
}