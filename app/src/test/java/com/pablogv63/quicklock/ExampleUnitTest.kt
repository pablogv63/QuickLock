package com.pablogv63.quicklock

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    fun findSubstring(s: String, words: Array<String>): List<Int> {

        // Check if words is empty
        if (words.isEmpty()) return listOf()

        // Calculate substring length and word length
        val substringLength = words.size * words[0].length
        val wordLength = words[0].length

        // Search for substrings
        var sIndex = 0

        var coincidenceFound = false
        var auxWordList = words.toMutableList()

        var matchedWordsFound = 0
        var firstIndex = -1

        val matchedPermutationsIndexes = mutableListOf<Int>()

        while(sIndex < s.length) {

            if ((sIndex + wordLength) > s.length) break

            // Word [i] of s
            val substring = s.substring(sIndex, sIndex + wordLength)

            // Check if letter is the first letter of any word
            // Now checks the entire word but its the same really
            coincidenceFound = false
            for (word in auxWordList) {
                if (substring == word) {
                    // Save index if first coincidence
                    if (matchedWordsFound == 0) {
                        firstIndex = sIndex
                    }
                    coincidenceFound = true
                    matchedWordsFound +=1
                    auxWordList.remove(word)
                    break
                }
            }
            if (matchedWordsFound > 0){
                // If no new coincidence found, discard
                if (!coincidenceFound) {
                    auxWordList = words.toMutableList()
                    matchedWordsFound = 0
                    sIndex = firstIndex + 1
                    continue
                }
                sIndex += wordLength-1
            }

            // If full coincidence: add to result
            if (auxWordList.isEmpty()){
                matchedPermutationsIndexes.add(firstIndex)
                sIndex = firstIndex + 1
            }

            sIndex += 1
        }
        // Return empty list
        return matchedPermutationsIndexes.toList()
    }

    @Test
    fun checkPerms(){
        assertEquals(listOf(0,1,2,3,4,5,6,7,8,9,10),findSubstring("aaaaaaaaaaaaaa",arrayOf("aa","aa")))
    }
}