package com.pablogv63.quicklock.ui.tools

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

object Tools {

    /**
     * Copies the [content] to the clipboard
     */
    fun Context.copyTextToClipboard(
        label: String,
        content: String
    ){
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(label, content)
        clipboardManager.setPrimaryClip(clipData)
    }

    fun Context.getActivity(): ComponentActivity? = when (this) {
        is ComponentActivity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }

    // Preferences
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
}