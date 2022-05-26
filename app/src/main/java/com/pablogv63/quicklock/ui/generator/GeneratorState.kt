package com.pablogv63.quicklock.ui.generator

data class GeneratorState(
    val generatedPassword: String = "",
    val length: Int = 22,
    val uppercase: Boolean = true,
    val lowercase: Boolean = true,
    val numbers: Boolean = true,
    val special: Boolean = false
)