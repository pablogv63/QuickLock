package com.pablogv63.quicklock.ui.tools

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

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
}