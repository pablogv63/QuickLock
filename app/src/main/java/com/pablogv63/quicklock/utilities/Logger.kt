package com.pablogv63.quicklock.utilities

import android.util.Log
import androidx.annotation.VisibleForTesting

/**
 * Wrapping of Android Logger class to enable tests to deactivate logging
 * From: https://www.paradigmadigital.com/dev/testing-android-tests-rapidos-y-faciles/ -> Contexto de ejecución
 */
class Logger {
    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        var enabled = true
        fun d(tag: String, message: String){
            if(enabled) Log.d(tag, message)
        }
    }
}