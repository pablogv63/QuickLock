package com.pablogv63.quicklock.domain.use_case.generator

import kotlin.random.Random

class GeneratePassword {

    private val uppercaseChars: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val lowercaseChars: String = uppercaseChars.lowercase()
    private val numberChars: String = "0123456789"
    private val specialChars: String = "!@#$%^&*€"

    // From: https://www.techiedelight.com/generate-pseudo-random-password-kotlin/
    // https://medium.com/swlh/password-generator-library-for-android-1a60bbf7663e
    operator fun invoke(
        length: Int,
        includeUpperCase: Boolean,
        includeLowerCase: Boolean,
        includeNumbers: Boolean,
        includeSpecial: Boolean,
        hasToMatchAll: Boolean = true
    ): String {
        var characterSet = ""
        val matcherCharsList = mutableListOf<String>()
        // Uppercase
        if (includeUpperCase) {
            characterSet += uppercaseChars
            matcherCharsList.add(uppercaseChars)
        }
        // Lowercase
        if (includeLowerCase) {
            characterSet += lowercaseChars
            matcherCharsList.add(lowercaseChars)
        }
        // Numbers
        if (includeNumbers) {
            characterSet += numberChars
            matcherCharsList.add(numberChars)
        }
        // Special characters
        if (includeSpecial) {
            characterSet += specialChars
            matcherCharsList.add(specialChars)
        }
        // Check if none included
        if (characterSet.isEmpty()) return ""

        val random = Random(System.nanoTime())
        val password = StringBuilder()

        do {
            password.clear()
            for (i in 0 until length)
            {
                val rIndex = random.nextInt(characterSet.length)
                password.append(characterSet[rIndex])
            }
        } while (hasToMatchAll && !matchesAll(password.toString(), length, matcherCharsList))

        return password.toString()
    }

    /**
     * Checks if password has at least one of each char type
     */
    private fun matchesAll(
        password: String,
        length: Int,
        matcherCharsList: List<String>
    ): Boolean{
        var matches = 0
        matcherCharsList.map { matcherChars ->
            if (password.any { it in matcherChars }) { matches +=1 }
        }
        // Check for short length
        if (length <= matcherCharsList.size) {
            // 3 length, 4 matchers, 3 matches
            return length == matches
        }
        return matches == matcherCharsList.size
    }
}