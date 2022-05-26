package com.pablogv63.quicklock.ui.generator

sealed class GeneratorEvent{
    data class LengthChanged(val length: Int): GeneratorEvent()
    data class UppercaseToggleChanged(val uppercaseToggle: Boolean): GeneratorEvent()
    data class LowercaseToggleChanged(val lowercaseToggle: Boolean): GeneratorEvent()
    data class NumbersToggleChanged(val numbersToggle: Boolean): GeneratorEvent()
    data class SpecialCharsToggleChanged(val specialCharsToggle: Boolean): GeneratorEvent()
    object RefreshPassword: GeneratorEvent()
    object Submit: GeneratorEvent()
}
